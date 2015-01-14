package org.venus.infra.web.security;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.venus.domain.user.entity.User;

@Repository
public class UserDAO {

	private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class);
			
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource ds) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(ds);
    }
    
    public User findByUsername(String username) {
    	String usernameParam = "uname";
    	Map<String, String> params = new HashMap<String, String>();
    	params.put(usernameParam, username);
    	Map<String, Object> userRow = null;
    	try {
	    	userRow = jdbcTemplate.queryForMap(
	    			"select email_address, password, first_name, last_name from users where email_address = :" + usernameParam, params);
    	} catch (Exception e) {
    		// either EmptyResultDataAccessException or IncorrectResultSizeDataAccessException
    		LOG.error(username + " not found or more than one result returned?", e);
    		return null;
    	}
    	User user = new User();    		
    	user.setUsername((String) userRow.get("email_address"));
    	user.setPassword((String) userRow.get("password"));
    	user.setName((String) userRow.get("first_name") + " " + (String) userRow.get("last_name"));
    	return user;
    }
    
}
