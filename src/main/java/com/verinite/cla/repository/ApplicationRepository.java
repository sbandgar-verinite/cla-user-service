package com.verinite.cla.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.verinite.cla.model.Tenant;

public interface ApplicationRepository extends JpaRepository<Tenant, Integer> {

}
