package org.venus.infra.web.security.sso;


import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.MimeTypeUtils;
import org.venus.infra.util.HttpUtils;

public class RememberMeSSOAuthSucessHandler implements AuthenticationSuccessHandler {

	private static final Logger LOG = LoggerFactory.getLogger(RememberMeSSOAuthSucessHandler.class);
			
    @Autowired
//    @Qualifier("hashMapImpl")
    @Qualifier("ehCacheImpl")
    private SSOActiveSessionList sessions;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		LOG.debug("RememberMeSSOAuthSucessHandler onAuthenticationSuccess() called...");
		
		String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        SSOSession rdSession = SSOSession.create(username, HttpUtils.originatingIPAddressFromRequest(request));
        sessions.addSession(rdSession);
        LOG.debug("Creating SSO cookie and adding it to response: {}", rdSession.getRdSessionID());
        response.addCookie(rdSession.generateCookie());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MimeTypeUtils.TEXT_PLAIN_VALUE);
        response.getWriter().write(username);
        response.setStatus(HttpServletResponse.SC_OK);
	}


}
