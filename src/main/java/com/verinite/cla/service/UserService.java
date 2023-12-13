package com.verinite.cla.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.verinite.cla.model.User;

public interface UserService {
	UserDetailsService userDetailsService();

	List<User> getAllUser();
	
}
