/**
 * 
 */
package com.zcode.springrestserver.web.service.impl;

import com.zcode.springrestserver.client.model.UserModel;
import com.zcode.springrestserver.client.model.mapper.UserMapper;
import com.zcode.springrestserver.web.api.IUserAPI;
import com.zcode.springrestserver.web.service.IUserService;

/**
 * @author prayag
 * 
 */
public class UserService implements IUserService {

	// @Autowired
	private final IUserAPI userAPI;

	public UserService(IUserAPI userAPI) {
		this.userAPI = userAPI;
	}

	/**
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	@Override
	public UserModel login(String userName, String password) {
		return UserMapper.mapUser(userAPI.getUserByUsernameAndPassword(
				userName, password));
	}
}
