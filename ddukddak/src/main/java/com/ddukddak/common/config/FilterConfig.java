package com.ddukddak.common.config;


import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ddukddak.common.filter.LoginFilter;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<LoginFilter> loginFilter() {
		
		log.info("loginFilter");
		
		FilterRegistrationBean<LoginFilter> filter 
				= new FilterRegistrationBean<>(); 
		
		filter.setFilter(new LoginFilter());
		
		String[] filteringURL = {"/myPage/*", "/partner/*"}; //실제 웹주소로 적어야함
	
		filter.setUrlPatterns( Arrays.asList(filteringURL));
		
		filter.setName("loginFilter");
		
		filter.setOrder(1);
		
		return filter; 
	}
}
