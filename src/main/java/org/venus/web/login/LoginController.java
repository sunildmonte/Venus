package org.venus.web.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

@Controller
//@RequestMapping("/login")
public class LoginController {

    /** Login page view, resolved to /WEB-INF/views/login.jsp. **/
    private static final String LOGIN_PAGE_VIEW_NAME = "login/login-page";

    private static final String LANDING_PAGE_VIEW_NAME = "login/landing-page";

//    @Autowired
//    private CsrfTokenRepository csrfRepository;
    
    //    @RequestMapping("/")
//    public RedirectView rootPath() {
//        RedirectView rv = new RedirectView(LOGIN_PAGE_URL, true);
//        return rv;
//    }
    
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String displayLoginPage(Model model, HttpServletRequest request) {
        model.addAttribute("login", new Login());
//        model.addAttribute("csrft", csrfRepository.loadToken(request).getToken());
        return LOGIN_PAGE_VIEW_NAME;
    }

	@RequestMapping(value = "/home", method = RequestMethod.GET)
    public String displayLandingPage() {
        return LANDING_PAGE_VIEW_NAME;
    }

}
