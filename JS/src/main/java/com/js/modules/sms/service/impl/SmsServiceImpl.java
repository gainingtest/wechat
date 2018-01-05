package com.js.modules.sms.service.impl;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.js.modules.sms.service.SmsService;
import com.js.support.message.Message;
import com.js.support.util.BaseRule;

@Service
public class SmsServiceImpl implements SmsService {
	
	public static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);
	

	@Override
	public Message smsSend(String mobile, String mobileBusiType, String nationCode) throws Exception {
		if (!BaseRule.checkPhone(mobile) && !BaseRule.checkHKNumber(mobile)) {
			logger.info("手机发送验证码入参，手机号【{}】，手机号格式校验不通过", mobile, mobileBusiType);
			return new Message(false, "发送手机验证码失败,手机号码格式错误");
		}
		//TODO
		return new Message(true, "发送成功");
	}

	@Override
	public Message smsVertify(String mobile, String code, String mobileBusiType) throws Exception {
		if ((!BaseRule.checkPhone(mobile) && !BaseRule.checkHKNumber(mobile)) || !BaseRule.IsNumber(code)
				|| StringUtils.isBlank(mobile) || StringUtils.isBlank(code) || StringUtils.isBlank(mobileBusiType)) {
			logger.info("手机发送验证码验证入参，手机号【{}】，业务类型【{}】，验证码【{}】为空或不对", mobile, mobileBusiType, code);
			return new Message(false, "手机号或验证码格式不对");
		}
		// 手机验证码校验
		//TODO
		return new Message(true, "校验成功");
	}

}