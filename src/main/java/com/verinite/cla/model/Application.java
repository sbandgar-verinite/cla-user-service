package com.verinite.cla.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "application")
public class Application {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonProperty("application_number")
	private String applicationNumber;

	@JsonProperty("application_name")
	private String applicationName;

	@JsonProperty("status")
	private String status;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//	@JoinTable(name = "application_tenants", joinColumns = @JoinColumn(name = "tenant_id"), inverseJoinColumns = @JoinColumn(name = "application_id"))
	private List<Tenant> tenant = new ArrayList<>();

	public Application() {
	}

	public Application(Long id, String applicationNumber, String applicationName) {
		this.id = id;
		this.applicationNumber = applicationNumber;
		this.applicationName = applicationName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Tenant> getTenant() {
		return tenant;
	}

	public void setTenant(List<Tenant> tenant) {
		this.tenant = tenant;
	}
}
