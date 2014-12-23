/**
 * 
 */
package org.venus.domain.user.repository;

import java.util.List;

import org.venus.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findOneByUsername(String username);

    public List<User> findByUsernameLike(String username);

}
