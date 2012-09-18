/**
 * 
 */
package com.zcode.springrestserver.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zcode.springrestserver.web.domain.User;

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

	@RequestMapping(value = "/", method = RequestMethod.GET, headers = "Accept=*/*")
	public @ResponseBody
	User login() {
		User user = new User();
		user.setFullName("prayag");

		return user;
	}
}
