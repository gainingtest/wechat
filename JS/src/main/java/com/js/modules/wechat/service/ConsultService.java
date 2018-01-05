package com.js.modules.wechat.service;

import com.js.support.message.Message;

public interface ConsultService {
	public Message wantConsult(String userId, String msg) throws Exception;
}
