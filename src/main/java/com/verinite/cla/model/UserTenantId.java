package com.verinite.cla.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserTenantId implements Serializable {

    private Long userId;
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
