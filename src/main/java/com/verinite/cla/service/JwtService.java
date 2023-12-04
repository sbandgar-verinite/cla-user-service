package com.verinite.cla.service;

import org.apache.coyote.BadRequestException;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
	String extractUserName(String token);

	String generateToken(UserDetails userDetails);

	boolean isTokenValid(String token, UserDetails userDetails);

	String extractEmail(String token);

	void checkRoleBasedAccess(String userEmail, String requestUri, String method) throws BadRequestException;
}
