package com.verinite.cla.dto;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class RoleDto {

	private Long id;

	private String name;

	private Set<PrivilegeDto> privileges = new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public Set<PrivilegeDto> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<PrivilegeDto> privileges) {
		this.privileges = privileges;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
