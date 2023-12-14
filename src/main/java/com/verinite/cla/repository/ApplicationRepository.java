package com.verinite.cla.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.verinite.cla.model.Application;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	Optional<Application> findByApplicationNumber(String applicationNumber);

}
