package com.verinite.cla.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.verinite.cla.model.Application;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	Optional<Application> findByApplicationNumber(String applicationNumber);

	@Query("select a from Application a where a.id IN (select at.applicationId from ApplicationTenant at where at.tenantId IN (select t.id from Tenant t where t.id IN :tenantList))")
	List<Application> findByTenantId(List<Long> tenantList);

}
