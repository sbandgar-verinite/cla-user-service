package com.verinite.cla.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verinite.cla.model.Tenant;
import com.verinite.cla.repository.ApplicationRepository;
import com.verinite.cla.service.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationRepository tenantRepository;

	@Override
	public Tenant createTenant(Tenant tenant) {
		Tenant save = tenantRepository.save(tenant);
		return save;
	}

	@Override
	public List<Tenant> getAllTenant() {

		return tenantRepository.findAll();
	}

}
