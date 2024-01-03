package com.verinite.cla.dto;

import java.util.List;

public class UserDto {

	private Long id;

	private String username;

	private String email;

	private List<ApplicationDto> applications;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<ApplicationDto> getApplications() {
		return applications;
	}

	public void setApplications(List<ApplicationDto> applications) {
		this.applications = applications;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", username=" + username + ", email=" + email + "]";
	}

}
