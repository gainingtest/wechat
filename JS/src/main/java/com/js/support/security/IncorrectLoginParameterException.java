package com.js.support.security;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 登录参数错误
 * @author Administrator
 *
 */
public class IncorrectLoginParameterException extends AuthenticationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8316467848015690440L;

	public IncorrectLoginParameterException() {
		super();
	}

	/**
	 * @param message
	 */
	public IncorrectLoginParameterException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public IncorrectLoginParameterException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public IncorrectLoginParameterException(String message, Throwable cause) {
		super(message, cause);
	}
}
