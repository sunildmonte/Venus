package org.venus.web.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

public class AuthenticationFilter extends GenericFilterBean {

	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		LOG.debug("AuthenticationFilter successfulAuthentication() called...");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		LOG.debug("AuthenticationFilter auth = {}", auth);
		
		chain.doFilter(request, response);
		
	}
	
//	public AuthenticationFilter() {
//		super();
//	}
	
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//            Authentication authResult) throws IOException, ServletException {
//    	
//    	LOG.debug("AuthenticationFilter successfulAuthentication() called...");
//    	
//    	super.successfulAuthentication(request, response, chain, authResult);
//    	
//    }

}
