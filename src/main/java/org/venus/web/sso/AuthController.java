package org.venus.web.sso;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.venus.infra.web.security.sso.SSOActiveSessionList;
import org.venus.infra.web.security.sso.SSOSession;

@RestController
public class AuthController {

	@Autowired
//	@Qualifier("hashMapImpl")
	@Qualifier("ehCacheImpl")
	private SSOActiveSessionList sessions;

	@RequestMapping(value = "/rds", method = RequestMethod.GET)
	public String authCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		response.setContentType("text/plain");
//		response.setCharacterEncoding("UTF-8");
		String rdSessionID = SSOSession.extractSessionIDFromCookie(request);
		SSOSession sess = sessions.lookupValidSession(rdSessionID);
		if (sess == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "User is not logged in.");
			return null;
		}
		return sess.getUsername();
	}


}
