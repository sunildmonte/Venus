package org.venus.infra.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.venus.infra.web.security.AuthenticationFilter;
import org.venus.infra.web.security.sso.SSOAuthSucessHandler;
import org.venus.infra.web.security.sso.SSOLogoutSuccessHandler;


@Configuration
@ComponentScan("org.venus.infra.web.security")
@EnableWebSecurity(debug = true) // have put this only to enable the debugging. should be commented out later.
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private AuthenticationManagerBuilder authManagerBuilder;
    
    // see http://stackoverflow.com/questions/19798863/authenticationmanager-when-updating-to-spring-security-3-2-0-rc2/19814105#19814105
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
             .inMemoryAuthentication()
                  .withUser("user")
                       .password("password")
                       .roles("USER");
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http
	        .authorizeRequests()
	        	.antMatchers("/rds/**").permitAll()
	            .anyRequest().authenticated()
	            .and()
	        .formLogin()
	            .loginPage("/login") 
	            .permitAll()
	            .successHandler(ssoSuccessHandler())
	            .and()
	        .logout()
	        	.logoutSuccessHandler(ssoLogoutHandler())
	         ;

	    
	    //.addFilter(customAuthFilter());
	            //.addFilterAfter(customAuthFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
//	@Bean
//	public Filter customAuthFilter() {
//		AuthenticationFilter af = new AuthenticationFilter();
//		//af.setAuthenticationManager(authenticationManager());
//		return af;
//	}
	
	@Bean
	public AuthenticationSuccessHandler ssoSuccessHandler() {
		return new SSOAuthSucessHandler("/home");
	}

	@Bean
	public LogoutSuccessHandler ssoLogoutHandler() {
		SSOLogoutSuccessHandler handler = new SSOLogoutSuccessHandler();
		handler.setDefaultTargetUrl("/login");
		return handler;
	}

//	@Bean
//	public AuthenticationManager authenticationManager() {
//		try {
//			return authManagerBuilder.build();
//		} catch (Exception e) {
//			throw new RuntimeException("This should not happen", e);
//		}
//	}
}
