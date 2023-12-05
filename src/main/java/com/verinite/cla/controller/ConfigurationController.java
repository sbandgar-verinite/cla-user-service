package com.verinite.cla.controller;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/config")
@RequiredArgsConstructor
public class ConfigurationController {

	@Autowired
	private ConfigService configService;

	@PostMapping("/{key}")
	public ResponseEntity<StatusResponse> addConfiguration(@PathVariable String key, @RequestBody @Valid Object value)
			throws BadRequestException {
		return ResponseEntity.ok(configService.addConfiguration(key, value));
	}

	@GetMapping("/{key}")
	public ResponseEntity<Object> getConfiguration(@PathVariable String key) throws BadRequestException {
		return ResponseEntity.ok(configService.getConfiguration(key));
	}

	@PostMapping
	public ResponseEntity<String> mapRolesToPrivilege(@RequestBody @Valid RoleDto role) throws BadRequestException {
		return ResponseEntity.ok(configService.mapRolesToPrivilege(role));
	}

	@PostMapping("/role")
	public ResponseEntity<Object> addRole(@RequestBody List<RoleDto> roleDto) {
		return ResponseEntity.ok(configService.addRole(roleDto));
	}

	@GetMapping("/role")
	public ResponseEntity<List<RoleDto>> getAllRoles() {
		return ResponseEntity.ok(configService.getAllRoles());
	}

	@PostMapping("/privilege")
	public ResponseEntity<Object> addPrivileges(@RequestBody @Valid List<PrivilegeDto> privilegeDto) {
		return ResponseEntity.ok(configService.addPrivileges(privilegeDto));
	}

	@GetMapping("/privilege")
	public ResponseEntity<Object> getAllPrivileges() {
		return ResponseEntity.ok(configService.getAllPrivileges());
	}

	@PostMapping("/endpoint")
	public ResponseEntity<Object> addEndpoint(@RequestBody @Valid List<EndpointDto> endpointDto) {
		return ResponseEntity.ok(configService.addEndpoint(endpointDto));
	}

	@GetMapping("/endpoint")
	public ResponseEntity<Object> getAllEndpoints() {
		return ResponseEntity.ok(configService.getAllEndpoints());
	}
}