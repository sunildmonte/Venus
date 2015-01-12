package org.venus.infra.web.security;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.venus.domain.user.entity.User;

@Repository
public class UserDAO {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource ds) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(ds);
    }
    
    public User findByUsername(String username) {
    	String usernameParam = "uname";
    	Map<String, String> params = new HashMap<String, String>();
    	params.put(usernameParam, username);
    	Map<String, Object> u = jdbcTemplate.queryForMap(
    			"select email_address, password from users where email_address = :uname", params);
    	User user = new User();
    	user.setUsername((String) u.get("email_address"));
    	user.setPassword((String) u.get("password"));
    	return user;
    }
    
}
