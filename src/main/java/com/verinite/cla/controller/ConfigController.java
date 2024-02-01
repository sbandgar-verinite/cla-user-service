package com.verinite.cla.controller;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.dto.EndpointDto;
import com.verinite.cla.dto.PrivilegeDto;
import com.verinite.cla.dto.RoleDto;
import com.verinite.cla.dto.StatusResponse;
import com.verinite.cla.service.ConfigService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/config/v1")
@RequiredArgsConstructor
public class ConfigController {

	@Autowired
	private ConfigService configService;

//	@PostMapping("/{key}")
//	public ResponseEntity<StatusResponse> addConfiguration(@PathVariable String key, @RequestBody Object value)
//			throws BadRequestException {
//		return ResponseEntity.ok(configService.addConfiguration(key, value));
//	}
//
//	@GetMapping("/{key}")
//	public ResponseEntity<Object> getConfiguration(@PathVariable String key) throws BadRequestException {
//		return ResponseEntity.ok(configService.getConfiguration(key));
//	}

	@PatchMapping("/role/privilege")
	public ResponseEntity<StatusResponse> mapRolesToPrivilege(@RequestBody List<RoleDto> role) {
		return ResponseEntity.ok(configService.mapRolesToPrivilege(role));
	}

	@PatchMapping("/privilege/endpoint")
	public ResponseEntity<StatusResponse> mapPrivilegeToEndpoint(@RequestBody List<PrivilegeDto> privilege) {
		return ResponseEntity.ok(configService.mapPrivilegeToEndpoint(privilege));
	}

	@PostMapping("/role")
	public ResponseEntity<Object> addRole(@RequestBody List<RoleDto> roleDto) {
		return ResponseEntity.ok(configService.addRole(roleDto));
	}

	@GetMapping("/role")
	public ResponseEntity<List<RoleDto>> getAllRoles() {
		return ResponseEntity.ok(configService.getAllRoles());
	}

	@GetMapping("/roles")
	public ResponseEntity<List<RoleDto>> getRolesData() {
		return ResponseEntity.ok(configService.getRolesData());
	}

	@PostMapping("/privilege")
	public ResponseEntity<Object> addPrivileges(@RequestBody List<PrivilegeDto> privilegeDto) {
		return ResponseEntity.ok(configService.addPrivileges(privilegeDto));
	}

	@GetMapping("/privilege")
	public ResponseEntity<Object> getAllPrivileges() {
		return ResponseEntity.ok(configService.getAllPrivileges());
	}

	@PostMapping("/endpoint")
	public ResponseEntity<Object> addEndpoint(@RequestBody List<EndpointDto> endpointDto) {
		return ResponseEntity.ok(configService.addEndpoint(endpointDto));
	}

	@GetMapping("/endpoint")
	public ResponseEntity<Object> getAllEndpoints() {
		return ResponseEntity.ok(configService.getAllEndpoints());
	}

}