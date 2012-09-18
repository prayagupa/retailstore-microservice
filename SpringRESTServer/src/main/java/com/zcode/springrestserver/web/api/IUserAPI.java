/**
 * 
 */
package com.zcode.springrestserver.web.api;

import com.zcode.springrestserver.web.domain.User;

/**
 * @author prayag
 * 
 */
public interface IUserAPI {

	/**
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	User getUserByUsernameAndPassword(String userName, String password);

}
