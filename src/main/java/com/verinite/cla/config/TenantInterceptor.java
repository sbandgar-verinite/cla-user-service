package com.verinite.cla.config;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.verinite.cla.dto.ApplicationRowMapper;
import com.verinite.cla.dto.ApplicationTenantRowMapper;
import com.verinite.cla.dto.TenantRowMapper;
import com.verinite.cla.model.Application;
import com.verinite.cla.model.ApplicationTenant;
import com.verinite.cla.model.Tenant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TenantInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(TenantInterceptor.class);

	@Value("${spring.datasource.url}")
	private String defaultUrl;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;
	
	@Value("${spring.datasource.database}")
	private String database;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String tenantId = request.getHeader("tenant-id");
		String applicationId = request.getHeader("application-id");
		if (tenantId == null || tenantId.isEmpty()) {
			logger.error("Tenant-Id is required field");
			response.resetBuffer();
			response.setHeader("Content-Type", "application/json");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			try {
				response.getOutputStream()
						.print("{\"status\":\"Error\",\"code\":400,\"message\":[\"Tenant-Id is required field\"]}");
				response.flushBuffer();
				return false;
			} catch (IOException e) {
				logger.error("Error to set response");
			}
//			throw new IllegalStateException("Tenant-Id is required in the header");
		}

		MysqlDataSource mysqlDataSource = new MysqlDataSource();
		mysqlDataSource.setUrl(defaultUrl);
		mysqlDataSource.setPassword(password);
		mysqlDataSource.setUser(username);
		mysqlDataSource.setDatabaseName(database);

		if (!mysqlDataSource.getDatabaseName().equals(tenantId)) {
			try {
				Tenant tenant = new JdbcTemplate(mysqlDataSource).queryForObject(
						"select * from tenant where tenant_code = ?", new Object[] { tenantId }, new TenantRowMapper());

				Application application = new JdbcTemplate(mysqlDataSource).queryForObject(
						"select * from application where application_number = ?", new Object[] { applicationId },
						new ApplicationRowMapper());

				if (Objects.nonNull(tenant) && Objects.nonNull(application)) {
					ApplicationTenant applicationTenant = new JdbcTemplate(mysqlDataSource).queryForObject(
							"select * from application_tenant where application_id = ? and tenant_id = ?",
							new Object[] { application.getId(), tenant.getId() }, new ApplicationTenantRowMapper());
					if (Objects.isNull(applicationTenant)) {
						logger.error("Tenant-ID not matched");
						response.resetBuffer();
						response.setHeader("Content-Type", "application/json");
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						try {
							response.getOutputStream()
									.print("{\"status\":\"Error\",\"code\":400,\"message\":[\"Tenant-Id Not Found!\"]}");
							response.flushBuffer();
							return false;
						} catch (IOException e) {
							logger.error("Error to set response");
						}
					}
				} else {
					logger.error("Tenant-ID not found");
					response.resetBuffer();
					response.setHeader("Content-Type", "application/json");
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					try {
						response.getOutputStream()
								.print("{\"status\":\"Error\",\"code\":400,\"message\":[\"Tenant-Id Not Found!\"]}");
						response.flushBuffer();
						return false;
					} catch (IOException e) {
						logger.error("Error to set response");
					}
				}
			}
			catch (Exception ex) {
				throw new Exception("Unable to find tenant-id : "+ ex.getMessage());
			}
		}

		TenantContext.setCurrentTenant(tenantId);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		TenantContext.clear();
	}
}
