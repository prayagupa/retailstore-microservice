package com.zcode.springrestserver.web.api.impl;

import com.zcode.springrestserver.web.api.IUserAPI;
import com.zcode.springrestserver.web.domain.User;
import com.zcode.springrestserver.web.repository.UserRepository;

/**
 * 
 * @author prayag
 * 
 */
public class UserAPI implements IUserAPI {

	private final UserRepository userRepository;

	public UserAPI(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User getUserByUsernameAndPassword(String userName, String password) {
		User user = new User();
		user.setFullName("Prayag");
		return user;
		// return userRepository.findByUserNameAndPassword(userName, password);
	}

}
