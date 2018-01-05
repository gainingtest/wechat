package com.js.support.security;

/**
 * 
 */
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义登录
 * 
 * @author
 * 
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

	private static final Logger logger = LoggerFactory.getLogger(CustomFormAuthenticationFilter.class);

	public String captcha = "captcha";
	public String loginType = "loginType";
	public String openid = "openid";

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptcha());
	}

	protected String getLoginType(ServletRequest request) {
		return WebUtils.getCleanParam(request, getLoginType());
	}
	
	protected String getOpenid(ServletRequest request) {
		return WebUtils.getCleanParam(request, getOpenid());
	}

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request) == null ? "" : getUsername(request);
		String password = getPassword(request) == null ? "" : getPassword(request);
		String captcha = getCaptcha(request) == null ? "" : getCaptcha(request);
		String loginType = getLoginType(request) == null ? "" : getLoginType(request);
		String host = getHost(request) == null ? "" : getHost(request);
		logger.info("用户登录信息：登录类型【{}】", loginType);
		logger.info("用户登录信息：用户名【{}】", username);
		logger.info("用户登录信息：登录验证码【{}】", captcha);
		return new CustomUsernamePasswordToken(captcha, loginType, username, password.toCharArray(), openid, false, host);
	}

	/*
	 * 主要是针对登入成功的处理方法。对于请求头是AJAX的之间返回JSON字符串。
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		// ProxyDTO proxyDTO = (ProxyDTO) subject.getPrincipal();
		// subject.getSession().setAttribute("proxyDTO", proxyDTO);
		if (!isAjax(request)) {// 不是ajax请求
			issueSuccessRedirect(request, response);
		} else {
			String url = "";
			try {
				url = WebUtils.getSavedRequest(request).getRequestUrl();
			} catch (Exception ex) {
				logger.error("获取未登录路径异常", ex);
			}
			response.setContentType("text/plain;charset=utf-8");
			PrintWriter out = response.getWriter();
			// out.println("{\"code\":'true',message:'登入成功',url:'" + url +
			// "'}");
			out.println("{\"code\":\"true\",\"message\":\"登录成功\",\"url\":\"" + url + "\"}");
			out.flush();
			out.close();
		}
		return false;
	}

	/**
	 * 主要是处理登入失败的方法
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
		PrintWriter out = null;
		if (!isAjax(request)) {// 不是ajax请求
			setFailureAttribute(request, e);
			return true;
		}
		try {
			response.setContentType("text/plain;charset=utf-8");
			out = response.getWriter();
			String message = "";
			logger.info("登录错误【{}】", e.getMessage());
			if (message.indexOf("Exception") >= 0) {
				message = "系统正在维护中，请稍后再试";
			} else {
				message = e.getMessage();
			}
			out.println("{\"code\":\"false\",\"message\":\"" + message + "\"}");
			out.flush();
			out.close();
		} catch (IOException e1) {
			logger.info("登录失败【{}】",e1.getMessage());
		} finally {
			if (null != out) {
				out.flush();
				out.close();
			}
		}
		return false;
	}

	/**
	 * 所有请求都会经过的方法。
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if (isLoginRequest(request, response)) {
			if (isLoginSubmission(request, response)) {
				if (logger.isTraceEnabled()) {
					logger.trace("Login submission detected.  Attempting to execute login.");
				}
				HttpServletRequest rq = (HttpServletRequest) request;
				HttpSession session = rq.getSession(false);
				if (session == null) {
					session = rq.getSession();
				}
				response.setContentType("text/plain;charset=utf-8");
				PrintWriter out = response.getWriter();
				if (getCaptcha(request) == null) {
					logger.info("登录验证码输入错误");
					out.println("{\"code\":\"captchaError\",\"message\":\"请输入正确的验证码\"}");
					out.flush();
					out.close();
					return false;
				}
				// CaptchaService captchaService =
				// (CaptchaService)SpringUtils.getBean("captchaService");
				// if(!captchaService.vaild(getCaptcha(request),
				// session.getId())){
				// out.println("{\"code\":\"captchaError\",\"message\":\"请输入正确的验证码\"}");
				// out.flush();
				// out.close();
				// return false;
				// }
				return executeLogin(request, response);
			} else {
				if (logger.isTraceEnabled()) {
					logger.trace("Login page view.");
				}
				// allow them to see the login page ;)
				return true;
			}
		} else {
			if (logger.isTraceEnabled()) {
				logger.trace("Attempting to access a path which requires authentication.  Forwarding to the " + "Authentication url [" + getLoginUrl() + "]");
			}
			if (!isAjax(request)) {// 不是ajax请求
				saveRequestAndRedirectToLogin(request, response);
			} else {
				response.setContentType("text/plain;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("{\"code\":false,\"message\":\"login\"}");
				out.flush();
				out.close();
			}
			return false;
		}
	}

	private boolean isAjax(ServletRequest request) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		return "XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"));
	}

	/**
	 * @return the captcha
	 */
	public String getCaptcha() {
		return captcha;
	}

	/**
	 * @param captcha
	 *            the captcha to set
	 */
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	/**
	 * @return the loginType
	 */
	public String getLoginType() {
		return loginType;
	}

	/**
	 * @param loginType
	 *            the loginType to set
	 */
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	
}
