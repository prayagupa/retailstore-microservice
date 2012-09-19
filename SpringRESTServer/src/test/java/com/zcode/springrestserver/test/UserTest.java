/**
 * 
 */
package com.zcode.springrestserver.test;

import org.springframework.beans.factory.annotation.Autowired;

import com.zcode.springrestserver.client.model.UserModel;
import com.zcode.springrestserver.web.api.IUserAPI;

/**
 * @author prayag
 * 
 */
public class UserTest {
	@Autowired
	IUserAPI userAPI;

	public void createUser() {
		UserModel userModel = new UserModel();
		userModel.setFullName("Prayag");
	}
}
