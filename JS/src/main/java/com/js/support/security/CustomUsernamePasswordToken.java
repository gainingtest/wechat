package com.js.support.security;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 自定义登录字段
 * @author 
 *
 */
public class CustomUsernamePasswordToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 1L;
	private String captcha;//验证码
	private String loginType;//微信，官网
	private String openid;//微信openid
	
	public CustomUsernamePasswordToken(String captcha, String loginType, String username, char[] password,String openid, boolean rememberMe, String host) {
		super(username, password, rememberMe, host); 
		this.captcha = captcha;
		this.loginType = loginType;
		this.openid = openid;
	}
	
	
	public String getLoginType() {
		return loginType;
	}


	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	
	/**
	 * @return the captcha
	 */
	public String getCaptcha() {
		return captcha;
	}
	/**
	 * @param captcha the captcha to set
	 */
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getOpenid() {
		return openid;
	}


	public void setOpenid(String openid) {
		this.openid = openid;
	}



}
