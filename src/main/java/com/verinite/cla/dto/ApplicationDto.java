package com.verinite.cla.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

public class ApplicationDto {

	private Integer id;

	@JsonProperty("application_number")
	private String applicationNumber;

	@JsonProperty("application_name")
	private String applicationName;

	@JsonProperty("status")
	private String status;

	@JsonProperty("tenants")
	private List<TenantDto> tenants = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
