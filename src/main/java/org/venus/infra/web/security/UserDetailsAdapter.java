package org.venus.infra.web.security;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.venus.domain.user.entity.User;

/**
 * Class which adapts the eGov User entity to Spring's UserDetails interface. It also exposes additional eGov-specific 
 * fields, so that these can be available by casting the UserDetails obtained from the SecurityContext.
 */
public class UserDetailsAdapter implements UserDetails {
    
    private User user;
    private final Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
    
    public UserDetailsAdapter(User user) {
        this.user = user;
    }

    public UserDetailsAdapter() {
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getPwdExpiryDate() == null
                ||
                user.getPwdExpiryDate().compareTo(new Date()) > 0;
    }

    @Override
    public boolean isEnabled() {
        return true; //user.getIsActive().equals(ACTIVE.Y.toString());
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null &&
                user.getUsername().equals(((UserDetailsAdapter) obj).getUsername());
    }
    
    @Override
    public int hashCode() {
        return user.getUsername().hashCode();
    }
    
    public String getName() {
        return user.getName();
    }
    
    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return user.toString();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

}
