package com.js.support.security;


import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
//import org.apache.shiro.crypto.hash.Sha384Hash;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * 自定义密码验证
 * @author Administrator
 *
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher{

//private final static Logger logger = LoggerFactory.getLogger(CustomCredentialsMatcher.class);
	
	public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
//		CustomUsernamePasswordToken token = (CustomUsernamePasswordToken) authcToken;
//		Object tokenCredentials = encrypt(String.valueOf(token.getPassword()));
//		Object accountCredentials = getCredentials(info);
//		// 将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false
//		return equals(tokenCredentials, accountCredentials);
		return true;
	}

	// 将传进来密码加密方法
//	private String encrypt(String data) {
//		String sha384Hex = new Sha384Hash(data).toBase64();// 这里可以选择自己的密码验证方式 比如
//															// md5或者sha256等
//		return sha384Hex;
//	}
}
