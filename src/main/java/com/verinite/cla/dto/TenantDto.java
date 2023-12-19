package com.verinite.cla.dto;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TenantDto {

	private Integer id;

	@JsonProperty("tenant_code")
	private String tenantCode;

	@JsonProperty("tenant_name")
	private String tenantName;

	@JsonProperty("status")
	private String status;

//	private Set<User> user = new HashSet<>();


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
