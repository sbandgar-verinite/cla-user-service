package com.verinite.cla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.model.User;
import com.verinite.cla.service.UserService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/details")
	public UserDetails getUserDetails(@RequestParam String email) {
		return userService.userDetailsService().loadUserByUsername(email);
	}
	
	@GetMapping
	public User getUserByEmail(@RequestParam String email) {
		return userService.findByEmail(email);
	}
}
