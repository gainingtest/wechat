package com.js.support.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SingleFilter implements Filter {
    private String serverLoginUrl;


    public void init(FilterConfig filterConfig) throws ServletException {
    }


    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String reqUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() 
        + httpServletRequest.getContextPath();
//        httpServletRequest.getRequestURI();
        Object username = httpServletRequest.getSession().getAttribute("username");
        if (username != null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String sid = httpServletRequest.getParameter("JSESSIONID");
        if (sid!=null) {
            Cookie cookie = new Cookie("JSESSIONID", sid);
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);
            String html = "<html><head><script type=\"text/javascript\">location.href='" + reqUrl + "'</script></head><body></body></html>";
            byte[] bytes = html.getBytes();
            httpServletResponse.setHeader("Content-Type", "text/html;charset=UTF-8");
            httpServletResponse.getOutputStream().write(bytes);
            httpServletResponse.getOutputStream().flush();
            httpServletResponse.getOutputStream().close();
            return;
        }

        httpServletResponse.sendRedirect(reqUrl+serverLoginUrl);
    }


    public void destroy() {

    }

    public String getServerLoginUrl() {
        return serverLoginUrl;
    }

    public void setServerLoginUrl(String serverLoginUrl) {
        this.serverLoginUrl = serverLoginUrl;
    }
}