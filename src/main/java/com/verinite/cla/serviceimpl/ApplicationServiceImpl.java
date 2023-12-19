package com.verinite.cla.serviceimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.verinite.cla.controlleradvice.BadRequestException;
import com.verinite.cla.dto.ApplicationDto;
import com.verinite.cla.dto.RoleDto;
import com.verinite.cla.dto.StatusResponse;
import com.verinite.cla.dto.TenantDto;
import com.verinite.cla.dto.UserDto;
import com.verinite.cla.model.Application;
import com.verinite.cla.model.Role;
import com.verinite.cla.model.Tenant;
import com.verinite.cla.model.User;
import com.verinite.cla.repository.ApplicationRepository;
import com.verinite.cla.repository.TenantRepository;
import com.verinite.cla.repository.UserRepository;
import com.verinite.cla.service.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);

	@Autowired
	private ApplicationRepository applicationRepo;

	@Autowired
	private ObjectMapper modelMapper;

	@Autowired
	private TenantRepository tenantRepository;

	@Value("${spring.datasource.host}")
	private String dbHost;

	@Value("${spring.datasource.username}")
	private String dbUser;

	@Value("${spring.datasource.password}")
	private String dbPass;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Tenant covertTenantDtoToTenant(TenantDto tenantDto) {
		return modelMapper.convertValue(tenantDto, Tenant.class);
	}

	private TenantDto convertTenantToTenantDto(Tenant tenant) {
		TenantDto tenantDto = new TenantDto();
		tenantDto.setId(tenant.getId());
		tenantDto.setStatus(tenant.getStatus());
		tenantDto.setTenantCode(tenant.getTenantCode());
		tenantDto.setTenantName(tenant.getTenantName());
		return tenantDto;
	}

	@Override
	public TenantDto createTenant(TenantDto tenantDto) {

		if (tenantDto == null) {
			throw new BadRequestException("Invalid Tenant Data");
		}

		Optional<Tenant> byTenantCode = tenantRepository.findByTenantCode(tenantDto.getTenantCode());

		if (!byTenantCode.isEmpty()) {
			throw new BadRequestException("Application Number Already Present  |  Duplication Occur ");
		}

		Tenant tenent = covertTenantDtoToTenant(tenantDto);

		Tenant saveTenant = tenantRepository.save(tenent);

		TenantDto tenantDto2 = convertTenantToTenantDto(saveTenant);

		return tenantDto2;

	}

	@Override
	public List<TenantDto> getAllTenant() {
		List<Tenant> tenantList = tenantRepository.findAll();
		return tenantList.stream().map(this::convertTenantToTenantDto).toList();
	}

	@Override
	public StatusResponse onboardTenant(ApplicationDto applicationDto) {
		if (applicationDto == null || applicationDto.getApplicationNumber() == null) {
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
//		applicationData.get().getTenants().addAll(tenantList);
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

	@Override
	public ApplicationDto createApplication(ApplicationDto applicationDto) {

		if (applicationDto == null || applicationDto.getApplicationNumber().isEmpty() || applicationDto.getApplicationNumber() == "") {
			throw new BadRequestException("Invalid Application Data");
		}
		
		//applicationDto == null || applicationDto.getApplicationNumber().isEmpty() || applicationDto.getApplicationNumber() == ""

		Optional<Application> byApplicationNumber = applicationRepo
				.findByApplicationNumber(applicationDto.getApplicationNumber());

		if (!byApplicationNumber.isEmpty()) {
			throw new BadRequestException("Application Number Already Present  |  Duplication Occur ");
		}

		Application application = convertApplicationDtoToApplication(applicationDto);

		Application save = applicationRepo.save(application);

		return convertApplicationToApplicationDto(save);
	}

	private Application convertApplicationDtoToApplication(ApplicationDto applicationDto) {
		return modelMapper.convertValue(applicationDto, Application.class);
	}

	private ApplicationDto convertApplicationToApplicationDto(Application application) {
		ApplicationDto applicationDto = new ApplicationDto();

		applicationDto.setApplicationName(application.getApplicationName());
		applicationDto.setApplicationNumber(application.getApplicationNumber());
		applicationDto.setStatus(application.getStatus());

		return applicationDto;
	}

	@Override
	public List<Application> getAllApplication(String appNum) {
		if (appNum == null) {
			return applicationRepo.findAll();
		} else {
			List<Application> application = new ArrayList<>();
			application.add(applicationRepo.findByApplicationNumber(appNum).get());
			return application;
		}
	}

	@Override
	public Tenant updateTenantStatus(Integer id, String status) {

		Optional<Tenant> tenant = tenantRepository.findById(id);

		tenant.get().setStatus(status);

		Tenant save = tenantRepository.save(tenant.get());

		return save;
	}

	@Override
	public ApplicationDto updateApplicationStatus(String appName, String status) {

		if (appName == null && status == null) {
			throw new BadRequestException(" Invalid input");
		}

		Optional<Application> byApplicationNumber = applicationRepo.findByApplicationNumber(appName);

		byApplicationNumber.get().setStatus(status);

		applicationRepo.save(byApplicationNumber.get());

		ApplicationDto applicationDto = convertApplicationToApplicationDto(byApplicationNumber.get());

		return applicationDto;
	}

	@Override
	public Tenant getTenantDetails(Integer id) {
		Optional<Tenant> findById = tenantRepository.findById(id);
		if (findById.isPresent()) {
			findById.get();
		}
		return null;
	}

	private TenantDto mapToTenantDto(TenantDto tenantDto) {

		TenantDto teDto = new TenantDto();
		teDto.setStatus(tenantDto.getStatus());
		return tenantDto;
	}

	@Override
	public StatusResponse mapApplicationTenantUser(ApplicationDto applicationDto) {
		logger.info("Request received to map application to user");
		if (applicationDto == null) {
			throw new BadRequestException("Invalid Application Data");
		}

		Optional<Application> applicationData = applicationRepo
				.findByApplicationNumber(applicationDto.getApplicationNumber());

		if (applicationData.isEmpty()) {
			throw new BadRequestException("Application Not Found for requested application number");
		}

		if (CollectionUtils.isEmpty(applicationDto.getTenants())) {
			throw new BadRequestException("Empty Tenant List not accepted for Mapping Users");
		}

		for (TenantDto tenant : applicationDto.getTenants()) {
			if (tenant.getTenantCode() != null) {
				Optional<Tenant> tenantData = tenantRepository.findByTenantCode(tenant.getTenantCode());
				if (tenantData.isEmpty()) {
					throw new BadRequestException("Tenant Data Not Found");
				}

				if (!CollectionUtils.isEmpty(tenant.getUsers())) {
					for (UserDto user : tenant.getUsers()) {
						Optional<User> userData = userRepo.findByEmail(user.getEmail());
						if (userData.isPresent() && !tenantData.get().getUser().contains(userData.get())) {
							tenantData.get().getUser().add(userData.get());
						}
					}
				}

				if (!applicationData.get().getTenants().contains(tenantData.get())) {
					applicationData.get().getTenants().add(tenantData.get());
				}
			}
		}

		logger.info("User Tenant Mapped succesfully");
		return new StatusResponse("Success", HttpStatus.OK.value(), "User Tenant Mapped Successfully");
	}

}
