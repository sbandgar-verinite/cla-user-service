package com.verinite.cla.service;

import java.util.List;

import com.verinite.cla.model.Tenant;

public interface ApplicationService {

	public Tenant createTenant(Tenant tenant);

	
	
	List<Tenant> getAllTenant();
}
