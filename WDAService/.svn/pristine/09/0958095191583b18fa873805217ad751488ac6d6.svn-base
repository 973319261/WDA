package com.gx.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginCheckFilter
 */
public class LoginCheckFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public LoginCheckFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession();

		//-1表示不存在该url
		if (session.getAttribute("admin") == null && request.getRequestURI().indexOf("/loginsPage.do") == -1
				&& request.getRequestURI().indexOf("/login.do") == -1 && request.getRequestURI().indexOf("/app") == -1) {
			//&& request.getRequestURI().indexOf("/swagger-ui.html") == -1
			//&& request.getRequestURI().indexOf("/toUrl.do") != -1
			// 没有登录
			/*if(request.getRequestURI().indexOf("swagger") == -1 || request.getRequestURI().indexOf("api-docs")!=-1) {
				chain.doFilter(arg0, arg1);
			}*/
			response.sendRedirect(request.getContextPath() + "/user/loginsPage.do");
		} else {
			// 已经登录，继续请求下一级资源（继续访问）
			chain.doFilter(arg0, arg1);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
