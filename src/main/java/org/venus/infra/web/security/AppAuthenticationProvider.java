package org.venus.infra.web.security;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AppAuthenticationProvider extends DaoAuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(AppAuthenticationProvider.class);
    
    @Autowired
    private UserDetailsService appUserDetailsService;
    
    public AppAuthenticationProvider() {
        super();
        setPasswordEncoder(new BCryptPasswordEncoder());
    }
    
    /**
     * Doing this in a @PostConstruct method because Spring will set autowired fields after construction.
     * The userDetService field would be null in the constructor.
     */
    @PostConstruct
    private void setAutowiredFields() {
        setUserDetailsService(appUserDetailsService);
    }
}
