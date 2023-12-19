package com.verinite.cla.service;

import java.util.List;

import com.verinite.cla.dto.ApplicationDto;
import com.verinite.cla.dto.StatusResponse;
import com.verinite.cla.dto.TenantDto;
import com.verinite.cla.model.Application;
import com.verinite.cla.model.Tenant;

public interface ApplicationService {

	public Tenant createTenant(TenantDto tenantDto);

	List<Tenant> getAllTenant();

	public StatusResponse onboardTenant(ApplicationDto applicationDto);

	public Application createApplication(ApplicationDto applicationDto);
	
	public Tenant updateTenantStatus(Integer id,String status);

}
