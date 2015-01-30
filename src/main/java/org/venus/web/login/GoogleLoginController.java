package org.venus.web.login;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.venus.infra.config.properties.AppProperties;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;
import com.google.api.services.plus.model.Person.Emails;

//@Controller
@RestController
public class GoogleLoginController {

	/*
	* Default HTTP transport to use to make HTTP requests.
	*/
	private static final HttpTransport TRANSPORT = new NetHttpTransport();
	/*
	* Default JSON factory to use to deserialize JSON.
	*/
	private static final JacksonFactory JSON_FACTORY = new JacksonFactory();
	/*
	* Gson object to serialize JSON responses to requests to this servlet.
	*/
//	private static final Gson GSON = new Gson();	

	private static final Logger LOG = LoggerFactory.getLogger(GoogleLoginController.class);
			
	@Autowired
	private CsrfTokenRepository csrfRepository;
	
	@Autowired
	private AppProperties appProperties;
	
	@RequestMapping(value = "/googlelogin", method = RequestMethod.POST)
	public String loginUsingGoogle(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("code") String googleCode,
			@RequestParam("state") String state
			) 
					throws IOException {

//		String gPlusId = "hello"; // request.queryParams("gplus_id");
//		String code = "world"; // request.body();
		
		String clientID = appProperties.getProperty("google.clientid"); 
		String clientSecret = appProperties.getProperty("google.clientsecret"); 
		LOG.debug("Client ID = {}, Client secret = {}", clientID, clientSecret);
		
		LOG.debug("Form params received in request: {}, {}", googleCode, state);
		
		CsrfToken token = csrfRepository.loadToken(request);
		String tokenValue = token.getToken();
		LOG.debug("CSRF token existing in session = {}", tokenValue);
		
		if (!state.equals(tokenValue)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Login failed, please try again.");
			return null;
		}
		
		String name;
		String emailAddr;

		try {
			// Upgrade the authorization code into an access and refresh token.
			GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
					TRANSPORT, JSON_FACTORY, clientID, clientSecret, googleCode, "postmessage").execute();
			LOG.debug("GoogleTokenResponse = {}", tokenResponse);
			
			// Create a credential representation of the token data.
			GoogleCredential credential = new GoogleCredential.Builder()
					.setJsonFactory(JSON_FACTORY).setTransport(TRANSPORT)
					.setClientSecrets(clientID, clientSecret).build()
					.setFromTokenResponse(tokenResponse);
			LOG.debug("GoogleCredential = {}", credential);
			
			Plus plus = new Plus.Builder(TRANSPORT, JSON_FACTORY, credential).build();

			Person person = plus.people().get("me").execute();          
			name = person.getDisplayName();
			List<Emails> emails = person.getEmails();
			emailAddr = emails.get(0).getValue();
			
		} catch (Exception e) {
			LOG.error("Google login failed...", e);
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Login failed, please try again.");
			return null;
		}
		
		return "hi there " + name + ", " + emailAddr;
	}

}
