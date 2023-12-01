package com.verinite.cla.dto;

import java.util.HashSet;
import java.util.Set;

public class RoleDto {

	private Integer id;

	private String name;

	private Set<PrivilegeDto> privileges = new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public Set<PrivilegeDto> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<PrivilegeDto> privileges) {
		this.privileges = privileges;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
