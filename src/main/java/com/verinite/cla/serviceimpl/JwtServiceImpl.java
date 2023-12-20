package com.verinite.cla.serviceimpl;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import com.verinite.cla.controlleradvice.BadRequestException;
import com.verinite.cla.controlleradvice.ForbiddenException;
import com.verinite.cla.model.Endpoint;
import com.verinite.cla.model.Privilege;
import com.verinite.cla.model.Role;
import com.verinite.cla.model.User;
import com.verinite.cla.repository.UserRepository;
import com.verinite.cla.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtServiceImpl implements JwtService {

	@Autowired
	private UserRepository userRepo;

	@Value("${token.signing.key}")
	private String jwtSigningKey;

	@Value("${token.expiry}")
	private Long tokenExpiryInMinutes;

	@Override
	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	@Override
	public String extractEmail(String token) {
		Claims allClaims = extractAllClaims(token);
		return allClaims.get("email", String.class);
	}

	@Override
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	@Override
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String userName = extractEmail(token);
		return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
		final Claims claims = extractAllClaims(token);
		return claimsResolvers.apply(claims);
	}

	private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		extraClaims.put("email", userDetails.getUsername());
		return Jwts.builder().setClaims(extraClaims)
//        		.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * tokenExpiryInMinutes))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	@Override
	public void checkRoleBasedAccess(String userEmail, String requestUri, String httpMethod)
			throws BadRequestException {
		Optional<User> userData = userRepo.findByEmail(userEmail);
		if (userData.isEmpty()) {
			throw new BadRequestException("Login failed");
		}

		if (CollectionUtils.isEmpty(userData.get().getRoles())) {
			throw new BadRequestException("Roles Not Found");
		}

		Set<Role> role = userData.get().getRoles();
		Optional<Role> rolePrivilege = role.stream().findFirst();

		Boolean isFound = Boolean.FALSE;
		if (rolePrivilege.isPresent() && !CollectionUtils.isEmpty(rolePrivilege.get().getPrivileges())) {
			List<Endpoint> endpointList = new ArrayList<>();
			for (Privilege privilege : rolePrivilege.get().getPrivileges()) {
				endpointList.addAll(privilege.getEndpoints());
			}

			isFound = endpointList.stream().anyMatch(e -> new AntPathMatcher().match(e.getEndpointUri(), requestUri));
		}

		if (Boolean.FALSE.equals(isFound)) {
			throw new ForbiddenException("Access Denied. You don't have access to this resource");
		}
	}
}