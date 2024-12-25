package com.linkedin.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// this configuration is for adding interceptor for this service so, aa service ne je bhi request aavse ene pella interceptor this intercept karvama aavse and ema je bhi process thay chhe e thase pachi j request aagal vadhse
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private UserInterceptor userInterceptor;

	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		registry.addInterceptor(userInterceptor);
	}
}