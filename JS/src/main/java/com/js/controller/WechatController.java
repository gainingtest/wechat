package com.js.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.js.modules.wechat.service.WxReplayMessageService;

@Controller
public class WechatController {
	private static Logger logger = LoggerFactory.getLogger(WechatController.class);
	
	@Autowired
	private WxReplayMessageService wxReplyMessageService;
	/**
	 * 用户发送消息 show（）方法进行接受并返回响应信息
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/show")
	public ModelAndView show(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("微信自动回复开始");
		String meString = request.getMethod();
		if ("get".equalsIgnoreCase(meString)) {
			logger.info("微信自动回复进行中。。。");
			shows(request, response);
			return null;
		}
		response.setContentType("text/html; charset=utf-8");
		String resply = wxReplyMessageService.getResplyMes(request, response);
		
	    response.getWriter().print(resply);
		return null;
	}
	/**
	 * 验证微信号
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/shows")
	public ModelAndView shows(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("微信自动回复+shows");

		ModelAndView view = new ModelAndView();
		view.setViewName("/common/weChatMessage");

		String signature = StringUtils.trim(request.getParameter("signature"));
		String nonce = StringUtils.trim(request.getParameter("nonce"));
		String timestamp = StringUtils.trim(request.getParameter("timestamp"));
		String echostr = StringUtils.trim(request.getParameter("echostr"));

		logger.info("开发模式接受参数signature:【{}】,nonce【{}】,timestamp【{}】,echostr【{}】", signature, nonce, timestamp, echostr);

		response.setContentType("text/html; charset=utf-8");
		
		response.getWriter().print(echostr);
		
		return null;
	}
}
