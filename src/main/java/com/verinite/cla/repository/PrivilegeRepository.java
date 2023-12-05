package com.verinite.cla.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.verinite.cla.model.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {

	Optional<Privilege> findByName(String name);

	@Query("SELECT p FROM Privilege p where p.name IN (:privileges)")
	List<Privilege> findAllByName(List<String> privileges);

}