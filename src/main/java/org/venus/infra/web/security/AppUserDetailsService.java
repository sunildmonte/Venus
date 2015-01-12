package org.venus.infra.web.security;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.venus.domain.user.entity.User;

/**
 * Implementation of Spring Security's UserDetailsService, which returns a UserDetails object for the given
 * username.
 */
@Component
@Scope(SCOPE_SINGLETON)
public class AppUserDetailsService implements UserDetailsService {
    
    private static final Logger LOG = LoggerFactory.getLogger(AppUserDetailsService.class);
    
    @Autowired
    private UserDAO userDao;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userDao.findByUsername(username);
        LOG.debug("userDao.findByUsername {}: {}", username, u);
        if (u == null) {
            throw new UsernameNotFoundException("User not found by repository: " + username);
        }
        
        UserDetails userDetails = new UserDetailsAdapter(u);

        LOG.debug("Mapped user to UserDetails: {}", userDetails);
        return userDetails;
    }
}
