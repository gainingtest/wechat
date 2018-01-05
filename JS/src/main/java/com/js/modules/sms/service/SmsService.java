package com.js.modules.sms.service;

import com.js.support.message.Message;

public interface SmsService {
	/**
	 * 手机发送验证码
	 * @param mobile
	 * @param mobileBusiType
	 * @param effectiveTime
	 * @return
	 * @throws Exception 
	 */
	public Message smsSend(String mobile,String mobileBusiType,String nationCode) throws Exception;
	
	/**
	 * 手机发送验证码验证
	 * @param mobile
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	public Message smsVertify(String mobile,String code,String mobileBusiType) throws Exception;

}
