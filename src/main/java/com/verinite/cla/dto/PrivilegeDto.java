package com.verinite.cla.dto;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class PrivilegeDto {

	private Long id;

	private String name;

	private Set<EndpointDto> endpoints = new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<EndpointDto> getEndpoints() {
		return endpoints;
	}

	public void setEndpoints(Set<EndpointDto> endpoints) {
		this.endpoints = endpoints;
	}
}
