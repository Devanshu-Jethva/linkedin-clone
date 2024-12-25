package com.linkedin.auth;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

// aa service this feign client through je bhi service ne request jay e request ne intercept kari ema "X-User-Id" header add karva aa intercept use thay chhe 
@Component
public class FeinClientInterceptor implements RequestInterceptor {

	@Override
	public void apply(final RequestTemplate requestTemplate) {
		Long userId = UserContextHolder.getCurrentUserId();
		if (userId != null) {
			requestTemplate.header("X-User-Id", userId.toString());
		}
	}
}