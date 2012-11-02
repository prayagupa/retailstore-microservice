/**
 * 
 */
package com.zcode.springrestserver.web.controller;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zcode.springrestserver.web.domain.User;
import com.zcode.springrestserver.web.repository.UserRepository;
import com.zcode.springrestserver.web.service.IUserService;

/**
 * @author prayag
 * 
 */
@Controller
public class PersistenceController {
	IUserService userService;
	private final UserRepository userRepository;

	public PersistenceController(IUserService userService, UserRepository userRepository) {
		this.userService = userService;
		this.userRepository = userRepository;
	}

	@RequestMapping(value = "/persistence", method = RequestMethod.GET, headers = "Accept=*/*")
	public @ResponseBody
	Map<String, Object> setup() {
		User user = new User();
		user.setAuthority("ROLE_ADMIN");
		user.setFullName("Rod Johnson");
		user.setPassword("123456");
		User addedUser = userRepository.save(user);

		Map<String, Object> map = new HashedMap();
		map.put("id", addedUser.getId());
		map.put("fullName", addedUser.getFullName());
		return map;
		// return addedUser;
	}
}
