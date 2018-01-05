package com.js.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.js.modules.user.dao.mybatis.entity.user;
import com.js.modules.user.service.UserService;
import com.js.support.exception.CommonException;
import com.js.support.message.Message;
import com.js.support.security.CustomUsernamePasswordToken;
import com.js.support.util.AssistParameter;
import com.js.support.util.BaseRule;
import com.js.support.util.EncoderUtil;
import com.js.support.util.JacksonUtil;
import com.js.support.util.RandomValidateCode;

@Controller
public class UserController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	
	
	/**
	 * 跳转注册页面
	 * @param openid
	 * @return
	 */
	@RequestMapping(value="user/toRegister")
	public ModelAndView toRegisterPage(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		String openid = request.getParameter("openid");
		logger.info("跳转到注册页面，参数openid【{}】",openid);
		mv.setViewName("/member_center/member_regist");
		mv.addObject("openid", openid);
		return mv;
	}
	
	/**
	 * 跳转到登录页面
	 * 
	 * @return
	 */
	@RequestMapping("user/toLogin")
	public ModelAndView toLogin(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String openid = request.getParameter("openid");
		mv.addObject("openid", openid);
		if (StringUtils.isNotBlank(openid)) {
			user userInfo = userService.findUserInfoByOpenid(openid);
			String mobile = "";
			if (userInfo != null) {
				mobile = userInfo.getMobile();
			}
			mv.addObject("mobile", mobile);
		} else {
			mv.addObject("mobile", "");
		}
		mv.setViewName("/member_center/login");
		return mv;
	}
	
	/**
	 * 通过手机号进行登录
	 */
	@RequestMapping(value = "/loginByMobile")
	@ResponseBody
	public Message loginByMobile(HttpServletRequest request) {
		String openid = request.getParameter("openid");
		String mobile = request.getParameter("mobile");
		String password = request.getParameter("password");
		String code = request.getParameter("code");
		String sessionCode = request.getSession().getAttribute(RandomValidateCode.RANDOMCODEKEY) == null ? ""
				: request.getSession().getAttribute(RandomValidateCode.RANDOMCODEKEY).toString();
		request.getSession().setAttribute(RandomValidateCode.RANDOMCODEKEY,"");
		
		BaseRule.checkEquals(sessionCode, code);
		
		try {
			Subject user = SecurityUtils.getSubject();
			CustomUsernamePasswordToken c = new CustomUsernamePasswordToken(code, "weChatTypeByMobile", mobile,
					password.toCharArray(), openid, false, "");
			user.login(c);
			boolean isAuthenticated = SecurityUtils.getSubject().isAuthenticated();
			logger.info("是否登录【{}】", isAuthenticated);
			return new Message(true, "登录成功");
		} catch (CommonException ex) {
			logger.error("登录异常,登录方式不正确", ex.getLocalizedMessage());
			return new Message(false, ex.getMessage(), "1");
		} catch (UnknownAccountException uk) {
			logger.error("登录异常,该微信号未绑定用户或微信未注册用户", uk.getLocalizedMessage());
			return new Message(false, uk.getMessage(), "1");
//		} catch (CommonException ex) {
//			logger.error("登录异常,该用户未设置密码,请到找回密码处设置密码", ex.getLocalizedMessage());
//			return new Message(false, ex.getMessage(), "2");
		} catch (DisabledAccountException ex) {
			logger.error("登录错误", ex.getLocalizedMessage());
			return new Message(false, ex.getMessage(), "1");
		} catch (CredentialsException ex) {
			logger.error("登录错误", ex.getLocalizedMessage());
			return new Message(false, ex.getMessage(), "3");
		} catch (AuthenticationException ex) {
			logger.error("登录错误", ex.getLocalizedMessage());
			return new Message(false, ex.getMessage(), "1");
		}
	}

	/**
	 * 点击页面进行注册
	 */
	@RequestMapping(value="user/register")
	@ResponseBody
	public Message wxRegister(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Message msg=new Message();
		AssistParameter assistParameter = new AssistParameter();
		response.setHeader("Access-Control-Allow-Origin", "*");
		String openid = request.getParameter("openid");
		String smsCode = request.getParameter("smsCode");
		String sessionCode = request.getSession().getAttribute(RandomValidateCode.RANDOMCODEKEY) == null ? "" : request.getSession().getAttribute(RandomValidateCode.RANDOMCODEKEY).toString();
		String passwordLevel = request.getParameter("passwordLevel");
		Integer pwSecurityLevel = Integer.valueOf(passwordLevel);
		request.getSession().setAttribute(RandomValidateCode.RANDOMCODEKEY,"");
		String password=request.getParameter("password");
		String repeatPassword=request.getParameter("repeatPassword");
		if(StringUtils.isBlank(password)||StringUtils.isBlank(request.getParameter("repeatPassword"))){
			return new Message(false,"输入密码不能为空");	
		}
		if(!password.equals(repeatPassword)){
			return new Message(false,"两次密码不一致请重新输入");
		}
		
		user u=new user();
		u.setMobile(request.getParameter("mobile"));
		u.setPassword(EncoderUtil.encodeByMD5Substring(request.getParameter("password")));
		assistParameter.setCode(request.getParameter("code"));
		assistParameter.setSmsCode(smsCode);
		assistParameter.setSessionCode(sessionCode);
		//TODO  进行注册
		assistParameter.setMobileBusiType("login");
		logger.info("注册参数baseOperate【{}】，openid【{}】，assistParameter【{}】",JacksonUtil.objectToJson(u),openid,JacksonUtil.objectToJson(assistParameter));
		String weChatRegister = userService.weChatRegister(u, openid, assistParameter);
		
		Subject user = SecurityUtils.getSubject();
		CustomUsernamePasswordToken c = new CustomUsernamePasswordToken("","weChatTypeByOpenid","",null,openid,false,"");
		user.login(c);
		
		msg.setResult(true);
		msg.setInfo("恭喜您,注册成功!");
		msg.setObject(openid);
		return msg;
	}
	
	
	
}
