package com.verinite.cla.serviceimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.verinite.cla.controlleradvice.BadRequestException;
import com.verinite.cla.dto.ApplicationDto;
import com.verinite.cla.dto.StatusResponse;
import com.verinite.cla.dto.TenantDto;
import com.verinite.cla.model.Application;
import com.verinite.cla.model.Tenant;
import com.verinite.cla.repository.ApplicationRepository;
import com.verinite.cla.repository.TenantRepository;
import com.verinite.cla.service.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);

	@Autowired
	private ApplicationRepository applicationRepo;

	@Autowired
	private TenantRepository tenantRepository;

	@Value("${spring.datasource.host}")
	private String dbHost;

	@Value("${spring.datasource.username}")
	private String dbUser;

	@Value("${spring.datasource.password}")
	private String dbPass;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Tenant createTenant(Tenant tenant) {
		Tenant save = tenantRepository.save(tenant);
		return save;
	}

	@Override
	public List<Tenant> getAllTenant() {
		return tenantRepository.findAll();
	}

	@Override
	public StatusResponse onboardTenant(ApplicationDto applicationDto) {
		if (applicationDto == null || applicationDto.getApplicationNumber() != null) {
			throw new BadRequestException("Invalid Json Data");
		}
		if (CollectionUtils.isEmpty(applicationDto.getTenants())) {
			throw new BadRequestException("Empty Tenants for Onboarding");
		}
		Optional<Application> applicationData = applicationRepo
				.findByApplicationNumber(applicationDto.getApplicationNumber());
		if (applicationData.isEmpty()) {
			throw new BadRequestException("Resource Not Found");
		}
		List<Integer> tenantIds = applicationDto.getTenants().stream().map(TenantDto::getId).toList();
		List<Tenant> tenantList = tenantRepository.findAllById(tenantIds);
		if (tenantList.size() != applicationDto.getTenants().size()) {
			throw new BadRequestException("Incorrect Tenant Ids passed");
		}
		applicationData.get().getTenants().addAll(tenantList);
		applicationRepo.save(applicationData.get());
		String additionalParams = "?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true";
		for (Tenant tenant : tenantList) {
			String dbName = applicationData.get().getApplicationName().toLowerCase() + "_" + tenant.getTenantCode();
			String url = dbHost + dbName + additionalParams;
			Connection db = null;
			try {
				db = DriverManager.getConnection(url, dbUser, dbPass);
			} catch (SQLException e) {
				throw new BadRequestException("Database connection didn't established");
			}
			ScriptUtils.executeSqlScript(db, new ClassPathResource("cla_scripts.sql"));
		}
		logger.info("Tenant onboarded succesfully");
		return new StatusResponse("Success", HttpStatus.OK.value(), "Onboarded Tenant Successfully");
	}

}
