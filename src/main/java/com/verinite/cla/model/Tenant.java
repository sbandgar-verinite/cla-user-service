package com.verinite.cla.model;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tenant")
public class Tenant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonProperty("tenant_id")
	private String tenantId;

	@JsonProperty("tenant_name")
	private String tenantName;

	@JsonProperty("status")
	private String status;

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

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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
