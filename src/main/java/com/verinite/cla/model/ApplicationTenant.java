package com.verinite.cla.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@IdClass(ApplicationTenantId.class)
@Entity
@Table(name = "application_tenant")
public class ApplicationTenant {

	@Id
	@JsonProperty("application_id")
	@Column(name = "application_id")
	private Long applicationId;

	@Id
	@JsonProperty("tenant_id")
	@Column(name = "tenant_id")
	private Long tenantId;

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
}
