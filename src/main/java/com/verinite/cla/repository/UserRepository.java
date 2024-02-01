package com.verinite.cla.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.verinite.cla.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query("select u from User u where email IN (:emails)")
	List<User> findAllByEmail(List<String> emails);

//    @Query("SELECT u.tenants FROM User u WHERE u.email = :email")
//	List<Tenant> findTenantsByEmail(String email);
}