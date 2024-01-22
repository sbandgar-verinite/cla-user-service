package com.verinite.cla.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.verinite.cla.model.Application;

public class ApplicationRowMapper implements RowMapper<Application> {
	@Override
	public Application mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Application(rs.getLong("id"), rs.getString("application_number"), rs.getString("application_name"));
	}
}