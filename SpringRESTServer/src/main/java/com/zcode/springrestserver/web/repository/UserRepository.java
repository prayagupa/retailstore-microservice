/**
 * 
 */
package com.zcode.springrestserver.web.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.zcode.springrestserver.web.domain.User;

/**
 * @author prayag
 * 
 */
public interface UserRepository extends CrudRepository<User, Long> {
	@Query("select u from User u where u.userName=?1 and password=?2")
	User findByUserNameAndPassword(String username, String password);
}
