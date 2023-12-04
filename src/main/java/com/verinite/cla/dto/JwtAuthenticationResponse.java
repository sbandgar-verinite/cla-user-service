package com.verinite.cla.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class JwtAuthenticationResponse {

	@JsonProperty("token")
	private String token;

	@JsonProperty("email_id")
	private String emailId;

	@JsonProperty("role")
	private String role;

	@JsonProperty("name")
	private String name;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public JwtAuthenticationResponse(String token, String emailId, String role, String name) {
		this.token = token;
		this.emailId = emailId;
		this.role = role;
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}