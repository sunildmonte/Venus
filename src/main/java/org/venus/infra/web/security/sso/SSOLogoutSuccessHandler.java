package org.venus.infra.web.security.sso;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class SSOLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

	private static final Logger LOG = LoggerFactory.getLogger(SSOLogoutSuccessHandler.class);
			
    @Autowired
//  @Qualifier("hashMapImpl")
    @Qualifier("ehCacheImpl")
    private SSOActiveSessionList sessions;

	@Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
		
        String cookieSessionID = SSOSession.extractSessionIDFromCookie(request);
        sessions.removeSession(cookieSessionID);
        
        Cookie[] cookies = request.getCookies();
        if (cookies != null || cookies.length >= 1) {
	        for (Cookie cookie : cookies) {
	        	LOG.debug("Found cookie " + cookie.getName());
	        	if (cookie.getName().equals(SSOSession.SSO_COOKIE_NAME)) {
		            LOG.debug("Removing cookie " + cookie.getName());
		            cookie.setMaxAge(0);
		            cookie.setPath("/");
		            cookie.setValue(null);
		            response.addCookie(cookie);
	        	}
	        }
        }
		
        super.onLogoutSuccess(request, response, authentication);
    }

}
