/**
 * 
 */
package com.zcode.springrestserver.client.model.mapper;

import com.zcode.springrestserver.client.model.UserModel;
import com.zcode.springrestserver.web.domain.User;

/**
 * @author prayag
 * 
 */
public class UserMapper {
	public static UserModel mapUser(User user) {
		UserModel userModel = new UserModel();
		userModel.setFullName(user.getFullName());
		return userModel;
	}
}
