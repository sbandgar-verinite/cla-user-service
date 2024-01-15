package com.verinite.cla.serviceimpl;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.verinite.cla.dto.JwtAuthenticationResponse;
import com.verinite.cla.dto.SignUpRequest;
import com.verinite.cla.dto.SigninRequest;
import com.verinite.cla.dto.StatusResponse;
import com.verinite.cla.model.Role;
import com.verinite.cla.model.User;
import com.verinite.cla.repository.RoleRepository;
import com.verinite.cla.repository.UserRepository;
import com.verinite.cla.service.AuthenticationService;
import com.verinite.cla.service.JwtService;
import com.verinite.commons.controlleradvice.UnAuthorizedException;

import jakarta.servlet.http.HttpServletResponse;
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

	@Autowired
	private RoleRepository roleRepo;

	@Override
	public StatusResponse signup(SignUpRequest request) {
		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		if (!CollectionUtils.isEmpty(request.getRoles())) {
			for (Role role : request.getRoles()) {
				Optional<Role> roleData = roleRepo.findByName(role.getName());
				if (roleData.isPresent()) {
					if (!user.getRoles().contains(role) && !roleData.get().getUsers().contains(user)) {
						user.getRoles().add(roleData.get());
						roleData.get().getUsers().add(user);
						userRepository.save(user);
					}
				} else {
					user.getRoles().add(role);
					userRepository.save(user);
				}
			}
		}
		jwtService.generateToken(user);
		return new StatusResponse("Sucess", HttpStatus.CREATED.value(), "User Registered Successfully");
	}

	@Override
	public JwtAuthenticationResponse signin(SigninRequest request, HttpServletResponse response) throws IOException {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		} catch (RuntimeException e) {
			response.resetBuffer();
			response.setHeader("Content-Type", "application/json");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getOutputStream()
					.print("{\"status\":\"Error\",\"code\":401,\"message\":[\"Invalid email or password!\"]}");
			response.flushBuffer();
			return null;
		}
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
		var jwt = jwtService.generateToken(user);
		String role = user.getRoles() != null && user.getRoles().iterator().hasNext()
				? user.getRoles().iterator().next().getName()
				: null;
		return new JwtAuthenticationResponse(jwt, user.getEmail(), role, user.getName());
	}
}