package com.verinite.cla.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EndpointDto {

	private Integer id;

	private String name;

	@JsonProperty("endpoint_uri")
	private String endpointUri;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEndpointUri() {
		return endpointUri;
	}

	public void setEndpointUri(String endpointUri) {
		this.endpointUri = endpointUri;
	}
}
