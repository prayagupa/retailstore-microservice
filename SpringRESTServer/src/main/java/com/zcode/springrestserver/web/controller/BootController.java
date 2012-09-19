/**
 * 
 */
package com.zcode.springrestserver.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zcode.springrestserver.client.model.UserModel;
import com.zcode.springrestserver.web.service.IUserService;

/**
 * @author prayag
 * 
 */
@Controller
public class BootController {
	// @RequestMapping(value = "/login", method = RequestMethod.GET)
	// public String login_() {
	// return "home";
	// }
	IUserService userService;

	public BootController(IUserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET, headers = "Accept=*/*")
	public @ResponseBody
	UserModel login() {
		return userService.login("Prayag", "123456");
	}
}
