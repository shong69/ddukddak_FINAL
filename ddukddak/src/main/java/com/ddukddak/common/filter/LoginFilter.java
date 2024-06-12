package com.ddukddak.common.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginFilter implements Filter{

//	필터 제거 해야하는 주소들
	 private static final List<String> EXCLUDED_URLS = Arrays.asList("/partner/login", 
			 																									"/partner/signup", 
			 																									"/partner/interiorList", 
			 																									"/partner/interior/modeling/main");
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request; 
		HttpServletResponse resp = (HttpServletResponse)response; 
		
		 String path = req.getRequestURI().substring(req.getContextPath().length());

        if (EXCLUDED_URLS.contains(path)) {
            chain.doFilter(request, response);
            return;
        }

		
		HttpSession session = req.getSession(false); 
		
		if(session == null || (session.getAttribute("loginMember") == null && session.getAttribute("loginPartnerMember") == null)) {
			
			resp.sendRedirect("/loginError");
			
		} else {
			chain.doFilter(request, response); 
		}
		
	}
	

}
