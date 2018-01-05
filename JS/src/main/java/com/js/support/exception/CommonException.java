package com.js.support.exception;


/**
 * 自定义通用异常
 * @author
 *
 */
public class CommonException extends RuntimeException {

	private static final long serialVersionUID = -717870860504034613L;
	
	public CommonException(String message){
		super(message);
	}
	
	public CommonException(String message , Throwable cause){
		super(message , cause);
	}

}
