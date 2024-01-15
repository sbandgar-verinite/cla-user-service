package com.verinite.cla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.dto.JwtAuthenticationResponse;
import com.verinite.cla.dto.SignUpRequest;
import com.verinite.cla.dto.SigninRequest;
import com.verinite.cla.dto.StatusResponse;
import com.verinite.cla.service.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class AuthenticationController {
	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/signup")
	public ResponseEntity<StatusResponse> signup(@RequestBody SignUpRequest request) {
		return ResponseEntity.ok(authenticationService.signup(request));
	}

	@PostMapping("/signin")
	public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
		return ResponseEntity.ok(authenticationService.signin(request));
	}

	@GetMapping("/login")
    public String login(HttpServletRequest request) {
        String redirectUri = request.getRequestURL().toString().replace(request.getServletPath(), "/login/oauth2/code/keycloak");
        // Set the redirect URI dynamically
        return "redirect:" + redirectUri;
    }
}