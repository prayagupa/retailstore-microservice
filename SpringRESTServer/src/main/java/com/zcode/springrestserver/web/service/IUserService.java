/**
 * 
 */
package com.zcode.springrestserver.web.service;

import com.zcode.springrestserver.client.model.UserModel;

/**
 * @author prayag
 * 
 */
public interface IUserService {

	UserModel login(String userName, String password);

}
