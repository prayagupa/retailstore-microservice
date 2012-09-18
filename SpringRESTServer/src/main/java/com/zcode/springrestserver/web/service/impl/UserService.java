/**
 * 
 */
package com.zcode.springrestserver.web.service.impl;

import com.zcode.springrestserver.client.model.UserModel;
import com.zcode.springrestserver.client.model.mapper.UserMapper;
import com.zcode.springrestserver.web.api.impl.UserAPI;
import com.zcode.springrestserver.web.service.IUserService;

/**
 * @author prayag
 * 
 */
public class UserService implements IUserService {

	private final UserAPI userAPI;

	public UserService(UserAPI userAPI) {
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
