package com.verinite.cla.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.verinite.cla.model.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

	@Query("SELECT t FROM Tenant t where id IN (:tenantIds)")
	List<Tenant> findAllById(List<Long> tenantIds);

	Optional<Tenant> findByTenantCode(String tenantCode);

//	@Query("select t from Tenant t where t.id IN (select ut.tenantId from UserTenant ut where ut.userId IN (select u.id from User u where u.email = :email))")
//	List<Tenant> findByUser(String email);

	@Query("select t from Tenant t where t.id IN (select ut.tenantId from UserTenant ut where ut.userId IN (select u.id from User u where u.email = :email))")
	List<Tenant> findByUser(String email);
}
