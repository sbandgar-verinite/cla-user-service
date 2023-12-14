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

	@JsonProperty("created_on")
	private ZonedDateTime createdOn;

	@JsonProperty("created_by")
	private String createdBy;

	@JsonProperty("modified_on")
	private ZonedDateTime modifiedOn;

	@JsonProperty("modified_by")
	private String modifiedBy;

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

	public ZonedDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(ZonedDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public ZonedDateTime getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(ZonedDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
