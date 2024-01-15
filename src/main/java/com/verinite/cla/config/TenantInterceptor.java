package com.verinite.cla.config;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.verinite.cla.dto.TenantRowMapper;
import com.verinite.cla.model.Tenant;
import com.verinite.cla.serviceimpl.ApplicationServiceImpl;
import com.verinite.commons.controlleradvice.BadRequestException;

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

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String tenantId = request.getHeader("tenant-id");
		if (tenantId == null || tenantId.isEmpty()) {
			logger.error("Tenant-Id is required field");
			response.resetBuffer();
			response.setHeader("Content-Type", "application/json");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			try {
				response.getOutputStream().print("{\"status\":\"Error\",\"code\":400,\"message\":[\"Tenant-Id is required field\"]}");
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
		List<Tenant> tenantList = new JdbcTemplate(mysqlDataSource).query("select * from tenant",
				new TenantRowMapper());

		if (!CollectionUtils.isEmpty(tenantList)) {
			Boolean isFound = tenantList.stream().anyMatch(x -> x.getTenantCode().equals(tenantId));
			if (Boolean.FALSE.equals(isFound)) {
				logger.error("Tenant-ID not found");
				response.resetBuffer();
				response.setHeader("Content-Type", "application/json");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				try {
					response.getOutputStream().print("{\"status\":\"Error\",\"code\":400,\"message\":[\"Tenant-Id Not Found!\"]}");
					response.flushBuffer();
					return false;
				} catch (IOException e) {
					logger.error("Error to set response");
				}
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
