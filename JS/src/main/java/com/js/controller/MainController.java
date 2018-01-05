package com.js.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.js.modules.wechat.service.PersonalCenterService;
import com.js.support.dto.UserInfoDTO;
import com.js.support.message.Message;
import com.js.support.util.RandomValidateCode;
import com.js.support.util.ShieldUtil;

@Controller
@RequestMapping("/center")
public class MainController {

	private static Logger logger = LoggerFactory.getLogger(MainController.class);
	@Autowired
	private PersonalCenterService personalCenterService;
	
	@RequestMapping("/toIndex")
	public ModelAndView toIndex() {
		return new ModelAndView("redirect:/Pages/member_center/html/index.html");
	}
	/**
	 * 个人中心首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/homePage")
	@ResponseBody
	public Message homePage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Message message = new Message();
		response.setHeader("Access-Control-Allow-Origin", "*");
		HashMap<String, Object> result = new HashMap<String, Object>();
		UserInfoDTO userInfoDTO = (UserInfoDTO) SecurityUtils.getSubject().getPrincipal();// 获取到登录用户信息
		userInfoDTO = (UserInfoDTO) SecurityUtils.getSubject().getSession().getAttribute(userInfoDTO.getMobile());// 根据手

		String shieldPhoneNo = ShieldUtil.shieldPhoneNo(userInfoDTO.getMobile());
		result.put("userName", userInfoDTO.getUserName());
		result.put("mobile", shieldPhoneNo);
		result.put("noApplication", "1");// 我的申请
		result.put("myOrder", "1");
		result.put("myPolicy", "1");
		result.put("perfect", userInfoDTO.getUserDegree());// 资料完善度
		result.put("imgUrl", userInfoDTO.getImgUrl());
		message.setObject(result);
		message.setResult(true);
		return message;
	}
	/**
	 * 跳转到我要咨询
	 * @return
	 */
	@RequestMapping("/toMyAdvise")
	public ModelAndView toMyAdvise(){
		logger.info("跳转到我要咨询页面");
		return new ModelAndView("redirect:/Pages/member_center/html/my_advise.html");
	}
	
	/**
	 * 转到咨询页
	 */
	@RequestMapping("/myAdvise")
	@ResponseBody
	public Message myAdvise() throws Exception{
		Message msg = new Message();
		personalCenterService.wantConsult();
		msg.setResult(true);
		return msg;
	}
}
