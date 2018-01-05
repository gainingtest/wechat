package com.js.modules.wechat.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.js.modules.wechat.service.ConsultService;
import com.js.support.exception.CommonException;
import com.js.support.message.Message;
@Service
public class ConsultServiceImpl implements ConsultService {
	private static Logger logger = LoggerFactory.getLogger(ConsultServiceImpl.class);
	@Override
	public Message wantConsult(String userId, String msg) throws Exception {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(msg)) {
			logger.info("接口参数错误【{}，{}】", userId, msg);
			throw new CommonException("缺少参数");
		}
		Map<String,String> map = new HashMap<String,String	>();
		map.put("UserID", userId);
		map.put("Text", msg);
		Message message=new Message();
		return message;
	}

}
