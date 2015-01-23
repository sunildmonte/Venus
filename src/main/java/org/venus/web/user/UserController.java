package org.venus.web.user;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.venus.domain.user.entity.User;
import org.venus.infra.web.security.UserDAO;
import org.venus.infra.web.security.sso.SSOActiveSessionList;
import org.venus.infra.web.security.sso.SSOSession;

@Controller
public class UserController {

	@Autowired
//	@Qualifier("hashMapImpl")
	@Qualifier("ehCacheImpl")
	private SSOActiveSessionList sessions;

    @Autowired
    private UserDAO userDao;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public User getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String rdSessionID = SSOSession.extractSessionIDFromCookie(request);
		SSOSession sess = sessions.lookupValidSession(rdSessionID);
		if (sess == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "User is not logged in.");
			return null;
		}
		
		User user = userDao.findByUsername(sess.getUsername());
		// we don't want to reveal the following properties in the json
		user.setId(null);
		user.setPassword(null);
		
		return user;
	}


}
