package com.js.modules.user.service;

import com.js.modules.user.dao.mybatis.entity.user;
import com.js.support.util.AssistParameter;

public interface UserService {

	String weChatRegister(user u, String openid, AssistParameter assistParameter) throws Exception;

	user findByNumber(String mobile);

	user findUserInfoByOpenid(String openid);

}
