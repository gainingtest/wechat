package com.js.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.js.support.message.Message;
import com.js.support.util.BaseRule;
import com.js.support.util.RandomValidateCode;

/**
 * 
 * 获取4位数字的图片验证码的controller方法
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/captcha")
public class CaptchaController {

	private static final Logger logger = LoggerFactory.getLogger(CaptchaController.class);

	/**
	 * 请求图片验证码
	 * 
	 * @return
	 */
	@RequestMapping(value = "/drawing")
	public ModelAndView getImage() {
		return new ModelAndView("/common/image");
	}

	/**
	 * 校验验证码是否正确
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/validate")
	@ResponseBody
	public Message validate(HttpServletRequest request, HttpServletResponse response) {
		Message msg = new Message();
		Map<String, Object> map = new HashMap<String, Object>();
		String code = request.getParameter("code");
		String sessionCode = request.getSession().getAttribute(RandomValidateCode.RANDOMCODEKEY) == null ? "" : request.getSession().getAttribute(RandomValidateCode.RANDOMCODEKEY).toString();
		logger.info("图片验证码校验code【{}】，sessionCode【{}】", code, sessionCode);
		try {
			BaseRule.checkEquals(sessionCode, code);
			msg.setResult(true);
			map.put("state", true);
			map.put("note", "图片验证码正确");
			msg.setObject(map);
		} catch (Exception e) {
			// TODO: handle exception
			msg.setResult(true);
			map.put("state", false);
			map.put("note", e.getMessage());
			msg.setObject(map);
			return msg;
		}
		return msg;
	}
}
