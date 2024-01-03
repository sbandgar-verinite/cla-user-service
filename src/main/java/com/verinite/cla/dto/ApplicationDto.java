package com.verinite.cla.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public class ApplicationDto {

	private Long id;

	@JsonProperty("application_number")
	private String applicationNumber;

	@JsonProperty("application_name")
	private String applicationName;

	@JsonProperty("status")
	private String status;

	@JsonProperty("tenants")
	private List<TenantDto> tenants = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<TenantDto> getTenants() {
		return tenants;
	}

	public void setTenants(List<TenantDto> tenants) {
		this.tenants = tenants;
	}


}
