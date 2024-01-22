package com.verinite.cla.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.verinite.cla.model.ApplicationTenant;
import com.verinite.cla.model.Tenant;

public class ApplicationTenantRowMapper implements RowMapper<ApplicationTenant> {
	@Override
	public ApplicationTenant mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new ApplicationTenant(rs.getLong("application_id"), rs.getLong("tenant_id"));
	}
}