package com.verinite.cla.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ApplicationTenantId implements Serializable {

	private Long applicationId;
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
