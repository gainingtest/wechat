package com.js.modules.wechat.service.impl;


import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.js.modules.wechat.service.WxReplayMessageService;
import com.js.support.util.WeChatResplyUtil;

@Service
public class wxReplayMessageServiceImpl implements WxReplayMessageService {
	private final static Logger logger = LoggerFactory.getLogger(WxReplayMessageService.class);
	
	private static  WeChatResplyUtil weChatResplyUtil;
    

	/**
	 * 拼装响应信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "static-access" })
	public String getResplyMes(HttpServletRequest request, HttpServletResponse response) {
		logger.info("官微自动回复，拼装响应用户消息");
		String reply = "感谢您关注华夏保险";// 响应信息

		InputStream inputStream = null;
		try {
			// 获取request输入流
			inputStream = request.getInputStream();

			// 获取请求数据
			Map<String, String> requstDataMap = weChatResplyUtil.parseXmlToMap(inputStream);
			logger.info("微信自动回复-拼装响应信息，入参【{}】", requstDataMap);
			if (requstDataMap != null) {
				String msgType = requstDataMap.get("MsgType");// 消息类型
				String fromUserName = requstDataMap.get("FromUserName");// 发送请求用户的openid
				String toUserName = requstDataMap.get("ToUserName");// 公众号id
				logger.info("微信自动回复-拼装响应信息，接收到的消息入参：msgType【{}】,fromUserName【{}】,toUserName【{}】",msgType,fromUserName,toUserName);
				// 请求信息是text
				if (weChatResplyUtil.MESSAGE_TEXT.equals(msgType)) {
					logger.info("微信自动回复-拼装响应信息，接收到的消息类型为文本，内容【{}】", requstDataMap.get("Content"));

					// 根据消息关键字查询自动回复消息数据
//					WxReplyMessage wxReplyMessage = wxReplayMessageSupportServiceImpl
//							.findWxReplyMessageByMessageKey(requstDataMap.get("Content"));
//					if (wxReplyMessage == null) {
//						// 消息转发到多客服】
//						logger.info("微信自动回复-拼装响应信息转发至多客服");
//						reply = String.format(weChatResplyUtil.transfer_customer_service,
//								new Object[] { fromUserName, toUserName, Long.valueOf(new Date().getTime()),
//										"transfer_customer_service" });
//					} else {

						reply =String.format(weChatResplyUtil.transfer_customer_service,
								new Object[] { fromUserName, toUserName, Long.valueOf(new Date().getTime()),
								"transfer_customer_service" });
//					}

				}
			}
			return reply;
		} catch (Exception e) {
			logger.info("微信自动回复异常【{}】", e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.info("微信自动回复流关闭异常【{}】", e);
				}
			}
		}
		return reply;
	}
}
