package com.linkedin.auth;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// this Interceptor is used to intercept each request come to this service and get userId from "X-User-Id" header
@Component
public class UserInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws Exception {

		String userId = request.getHeader("X-User-Id");
		if (userId != null) {
			UserContextHolder.setCurrentUserId(Long.valueOf(userId));
		}

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
			final Object handler, final Exception ex) throws Exception {
		UserContextHolder.clear();
	}
}
