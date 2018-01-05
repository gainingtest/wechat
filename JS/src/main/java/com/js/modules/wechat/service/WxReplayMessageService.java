package com.js.modules.wechat.service;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WxReplayMessageService {
	
	/**
	 * 拼装响应信息
	 * 
	 * @return
	 * @throws IOException 
	 */
	public  String getResplyMes(HttpServletRequest request, HttpServletResponse response) throws IOException ;
    
}
