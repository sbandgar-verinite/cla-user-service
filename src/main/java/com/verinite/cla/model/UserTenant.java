package com.verinite.cla.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@IdClass(UserTenantId.class)
@Entity
@Table(name = "user_tenant")
public class UserTenant {

	@Id
	@JsonProperty("user_id")
	@Column(name = "user_id")
	private Long userId;

	@Id
	@JsonProperty("tenant_id")
	@Column(name = "tenant_id")
	private Long tenantId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
}
