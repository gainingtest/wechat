package com.js.support.security;


import org.apache.shiro.authc.AuthenticationException;

/**
 * 登录参数方式错误
 * @author Administrator
 *
 */
public class IncorrectLoginTypeException extends AuthenticationException {

	private static final long serialVersionUID = 2775436180954014245L;

	public IncorrectLoginTypeException() {
		super();
	}

	/**
	 * @param message
	 */
	public IncorrectLoginTypeException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public IncorrectLoginTypeException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public IncorrectLoginTypeException(String message, Throwable cause) {
		super(message, cause);
	}
}
