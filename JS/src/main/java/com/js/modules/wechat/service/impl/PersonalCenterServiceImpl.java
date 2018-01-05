package com.js.modules.wechat.service.impl;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.js.modules.wechat.service.PersonalCenterService;
import com.js.support.dto.UserInfoDTO;
@Service
public class PersonalCenterServiceImpl implements PersonalCenterService {
	private static Logger logger = LoggerFactory.getLogger(PersonalCenterServiceImpl.class);
	@Override
	public void wantConsult() throws Exception {
		UserInfoDTO userInfoDTO = (UserInfoDTO)SecurityUtils.getSubject().getPrincipal();
		String userId = userInfoDTO.getUserId();
		String msg = "msg";
		logger.info("调用我要咨询参数userId【{}】",userId);
	}

}
