package com.verinite.cla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.verinite.cla.model.Tenant;
import com.verinite.cla.model.User;
import com.verinite.cla.service.ApplicationService;
import com.verinite.cla.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1")
public class ApplicationController {

	@Autowired
	private ApplicationService tenantService;

	@Autowired
	private UserService userService;



	@PostMapping("/application/tenant/add")
	public ResponseEntity<Tenant> createTenant(@RequestBody Tenant tenant) {

		Tenant saveTenent = tenantService.createTenant(tenant);
		return new ResponseEntity<Tenant>(saveTenent, HttpStatus.CREATED);
	}

	@GetMapping("/application/tenant/get/all")
	public ResponseEntity<List<Tenant>> getAllTenants() {
		List<Tenant> allTenant = tenantService.getAllTenant();
		return new ResponseEntity<List<Tenant>>(allTenant, HttpStatus.OK);
	}

	@GetMapping("/application/users")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> allUser = userService.getAllUser();
		return new ResponseEntity<List<User>>(allUser, HttpStatus.OK);
	}



}
