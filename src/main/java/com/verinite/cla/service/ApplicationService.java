package com.verinite.cla.service;

import java.util.List;

import com.verinite.cla.dto.ApplicationDto;
import com.verinite.cla.dto.StatusResponse;
import com.verinite.cla.dto.TenantDto;
import com.verinite.cla.model.Application;
import com.verinite.cla.model.Tenant;

public interface ApplicationService {

    public TenantDto createTenant(TenantDto tenantDto);

    List<Tenant> getAllTenant();

    public StatusResponse onboardTenant(ApplicationDto applicationDto);

    public ApplicationDto createApplication(ApplicationDto applicationDto);

    public List<Application> getAllApplication(String appNum);

    public Tenant updateTenantStatus(Integer id, String status);

    public ApplicationDto updateApplicationStatus(String appName, String status);

    public Tenant getTenantDetails(Integer id);

}
