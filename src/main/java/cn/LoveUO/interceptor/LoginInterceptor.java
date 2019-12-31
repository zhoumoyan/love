package cn.LoveUO.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 获取session对象
		HttpSession session = request.getSession();
		// 判断session中有没有uid
		if (session.getAttribute("uid") == null) {
			// 没有uid，即未登录，或登录后超时，则重定向到登录页
			response.sendRedirect("/love/web/login.html");
			// 执行拦截
			return false;
		}
		// 放行
		return true;
	}
	
}
