package com.verinite.cla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.dto.ApplicationDto;
import com.verinite.cla.dto.StatusResponse;
import com.verinite.cla.dto.TenantDto;
import com.verinite.cla.model.Application;
import com.verinite.cla.model.Tenant;
import com.verinite.cla.model.User;
import com.verinite.cla.service.ApplicationService;
import com.verinite.cla.service.UserService;

@RestController
@RequestMapping("/auth/application")
public class ApplicationController {

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private UserService userService;

	@GetMapping("/tenant/get/all")
	public ResponseEntity<List<Tenant>> getAllTenants() {
		List<Tenant> allTenant = applicationService.getAllTenant();
		return new ResponseEntity<List<Tenant>>(allTenant, HttpStatus.OK);
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> allUser = userService.getAllUser();
		return new ResponseEntity<List<User>>(allUser, HttpStatus.OK);
	}

	@PostMapping("/tenant/onboard")
	public ResponseEntity<StatusResponse> onboardTenant(@RequestBody ApplicationDto applicationDto) {
		StatusResponse statusReponse = applicationService.onboardTenant(applicationDto);
		return new ResponseEntity<>(statusReponse, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<Application> createApplication(@RequestBody ApplicationDto applicationDto) {
		Application application = applicationService.createApplication(applicationDto);
		return new ResponseEntity<>(application, HttpStatus.CREATED);
	}

	@PostMapping("/tenant/add")
	public ResponseEntity<Tenant> createTenant(@RequestBody TenantDto tenantDto) {
		Tenant createTenant = applicationService.createTenant(tenantDto);
		return new ResponseEntity<>(createTenant, HttpStatus.CREATED);
	}
}
