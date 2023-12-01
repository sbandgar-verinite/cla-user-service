package com.verinite.cla.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.dto.RoleDto;
import com.verinite.cla.model.Role;
import com.verinite.cla.service.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/resource")
@RequiredArgsConstructor
public class AuthorizationController {

	@Autowired
	private RoleService roleService;

	@GetMapping
	public ResponseEntity<String> sayHello() {
		return ResponseEntity.ok("Here is your resource");
	}

	@PostMapping
	public ResponseEntity<String> mapRolesToPrivilege(@RequestBody RoleDto role) throws BadRequestException {
		return ResponseEntity.ok(roleService.mapRolesToPrivilege(role));
	}

}