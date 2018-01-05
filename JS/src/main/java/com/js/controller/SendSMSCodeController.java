package com.js.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.js.modules.sms.service.SmsService;
import com.js.support.dto.UserInfoDTO;
import com.js.support.message.Message;
import com.js.support.util.CodeTable;
import com.js.support.util.JacksonUtil;


/**
 * 短信发送公用方法
 * 
 * @author wang
 *
 */
@Controller
@RequestMapping("/common")
public class SendSMSCodeController {
	@Autowired
	private SmsService smsService;// 发送短信服务
	public static final Logger logger = LoggerFactory.getLogger(SendSMSCodeController.class);

	/**
	 * 保全申请发送短信验证码
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sendSMSCode")
	@ResponseBody
	public Message getSMSCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Message msg = new Message();
		String mobile = request.getParameter("mobile");
		String smsCode = request.getParameter("smsCode");
		UserInfoDTO userInfoDTO = (UserInfoDTO) SecurityUtils.getSubject().getPrincipal();
		String userMobile = userInfoDTO.getMobile();
		if (StringUtils.isBlank(mobile) || !userMobile.equals(mobile)) {
			msg.setResult(false);
			msg.setInfo("短信验证码发送失败，手机号码不存在！");
			return msg;
		}
		logger.info("申请短信发送手机号码【{}】,短信类型ID【{}】", mobile, smsCode);
		
		//TODO
		
		String mobileBusiType = "";
		msg = smsService.smsSend(mobile, mobileBusiType,"");
		logger.info("短信发送结果【{}】", JacksonUtil.objectToJson(msg));
		if (msg.getResult()) {
			msg.setInfo("短信验证码发送成功，请注意查收！");
			msg.setResult(true);
		} else {
			msg.setResult(false);
		}
		return msg;
	}

	/**
	 * 用户注册、找回密码使用
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/SMSSend")
	@ResponseBody
	public Message SMSSend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Message msg = new Message();
		String mobile = request.getParameter("mobile");
		String smsCode = request.getParameter("smsCode");
		logger.info("申请短信发送手机号码【{}】,短信类型ID【{}】", mobile, smsCode);
		//TODO
		String mobileBusiType = "";
		// 调用service发送验证码
		msg = smsService.smsSend(mobile, mobileBusiType, "");
		logger.info("短信发送结果【{}】", JacksonUtil.objectToJson(msg));
		if (msg.getResult()) {
			msg.setInfo("短信验证码发送成功，请注意查收！");
			msg.setResult(true);
		} else {
			msg.setResult(false);
		}
		return msg;
	}

	/**
	 * 短信验证码实时校验
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkSMSCode")
	@ResponseBody
	public Message checkSMSCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Message msg = new Message();
		String mobile = request.getParameter("mobile");
		String code = request.getParameter("code");
		String mobileBusiType = request.getParameter("mobileBusiType");
		logger.info("短信实时校验传入参数【mobile：{}】,验证码【code:{}】短信类型ID【{}】", mobile, code, mobileBusiType);
		msg = smsService.smsVertify(mobile, code, mobileBusiType);
		logger.info("短信实时校验结果【{}】", JacksonUtil.objectToJson(msg));
		if (msg.getResult()) {
			msg.setResult(true);
		} else {
			msg.setResult(false);
		}
		return msg;
	}

}
