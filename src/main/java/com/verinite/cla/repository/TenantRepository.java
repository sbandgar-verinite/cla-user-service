package com.verinite.cla.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.verinite.cla.model.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, Integer> {

	@Query("SELECT t FROM Tenant t where id IN (:tenantIds)")
	List<Tenant> findAllById(List<Integer> tenantIds);
	
	
	Optional<Tenant> findByTenantCode(String tenantCode);

}
