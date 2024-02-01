package com.verinite.cla.service;

import java.util.List;

import com.verinite.cla.dto.ApplicationDto;
import com.verinite.cla.dto.StatusResponse;
import com.verinite.cla.dto.TenantDto;

public interface ApplicationService {

	public StatusResponse createTenant(TenantDto tenantDto);
	List<TenantDto> getAllTenant(String applicationNumber);

	public StatusResponse onboardTenant(ApplicationDto applicationDto);

	public StatusResponse createApplication(ApplicationDto applicationDto);

	public ApplicationDto getApplicationDetails(String applicationNumber);

	public List<ApplicationDto> getAllApplicationDetails();

	//public Tenant updateTenantStatus(Integer id, String status);

	public TenantDto updateTenantStatus(String tenantCode, String status);
	public ApplicationDto updateApplicationStatus(String appName, String status);

//	public Tenant getTenantDetails(Integer id);

	public TenantDto getTenantDetails(String tenantCode);
	public StatusResponse onboardUser(TenantDto tenantDto);

}
