package com.verinite.cla.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.dto.StatusResponse;
import com.verinite.cla.service.ConfigService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth/config")
@RequiredArgsConstructor
public class ConfigurationController {

	@Autowired
	private ConfigService configService;

	@PostMapping("/{key}")
	public ResponseEntity<StatusResponse> addConfiguration(@PathVariable String key, @RequestBody Object value)
			throws BadRequestException {
		return ResponseEntity.ok(configService.addConfiguration(key, value));
	}

	@GetMapping("/{key}")
	public ResponseEntity<Object> getConfiguration(@PathVariable String key) throws BadRequestException {
		return ResponseEntity.ok(configService.getConfiguration(key));
	}
}