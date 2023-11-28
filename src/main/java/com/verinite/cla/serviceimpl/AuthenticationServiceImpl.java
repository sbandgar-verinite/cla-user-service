package com.verinite.cla.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.verinite.cla.dto.JwtAuthenticationResponse;
import com.verinite.cla.dto.SignUpRequest;
import com.verinite.cla.dto.SigninRequest;
import com.verinite.cla.dto.StatusResponse;
import com.verinite.cla.model.User;
import com.verinite.cla.repository.UserRepository;
import com.verinite.cla.service.AuthenticationService;
import com.verinite.cla.service.JwtService;
import com.verinite.cla.util.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public StatusResponse signup(SignUpRequest request) {
		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(Role.USER);
		userRepository.save(user);
		jwtService.generateToken(user);
		return new StatusResponse("Sucess", HttpStatus.CREATED.value(), "User Registered Successfully");
	}

	@Override
	public JwtAuthenticationResponse signin(SigninRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		var user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
		var jwt = jwtService.generateToken(user);
		return new JwtAuthenticationResponse(jwt);
	}
}