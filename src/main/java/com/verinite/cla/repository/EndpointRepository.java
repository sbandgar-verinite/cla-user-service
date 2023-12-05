package com.verinite.cla.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.verinite.cla.model.Endpoint;

@Repository
public interface EndpointRepository extends JpaRepository<Endpoint, Integer> {

	Optional<Endpoint> findByName(String name);

	@Query("SELECT e FROM Endpoint e where e.name IN (:endpoints)")
	List<Endpoint> findAllByName(List<String> endpoints);

}