package org.venus.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class SSOAuthSucessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private static final Logger LOG = LoggerFactory.getLogger(SSOAuthSucessHandler.class);
			
	public SSOAuthSucessHandler() {
		super();
	}

	public SSOAuthSucessHandler(String defaultTargetUrl) {
		super(defaultTargetUrl);
	}

	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
    		throws IOException, ServletException {
    	
		LOG.debug("SSOAuthSucessHandler onAuthenticationSuccess() called...");
		super.onAuthenticationSuccess(request, response, authentication);
    }

}
