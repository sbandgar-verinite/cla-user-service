package com.verinite.cla.serviceimpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.verinite.cla.dto.EndpointDto;
import com.verinite.cla.dto.PrivilegeDto;
import com.verinite.cla.dto.RoleDto;
import com.verinite.cla.dto.StatusResponse;
import com.verinite.cla.model.Endpoint;
import com.verinite.cla.model.Privilege;
import com.verinite.cla.model.Role;
import com.verinite.cla.repository.EndpointRepository;
import com.verinite.cla.repository.PrivilegeRepository;
import com.verinite.cla.repository.RoleRepository;
import com.verinite.cla.service.ConfigService;
import com.verinite.cla.util.Constants;
import com.verinite.commons.controlleradvice.BadRequestException;
import com.verinite.commons.model.Config;
import com.verinite.commons.repo.ConfigurationRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConfigServiceImpl implements ConfigService {

	private final Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);

	@Autowired
	private ConfigurationRepository configRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private ObjectMapper modelMapper;

	@Autowired
	private PrivilegeRepository privilegeRepository;

	@Autowired
	private EndpointRepository endpointRepository;

	@Override
	public StatusResponse addConfiguration(String key, Object value) throws BadRequestException {
		logger.info("[SERVICE] Request received to add configuration for key : {}", key);
		logger.debug("Value for key {} = {}", key, value);
		if (key == null) {
			throw new BadRequestException("Please pass a valid key");
		}
		if (value == null) {
			throw new BadRequestException("Please pass a valid value");
		}

		Optional<Config> existingConfig = configRepo.findByKeyName(key);
//		if (existingConfig.isPresent()) {
//			throw new BadRequestException("Duplicate Key. Key already exists");
//		}

		if(existingConfig.isEmpty()) {
			Config config = new Config();
			config.setKeyName(key);
			try {
				config.setData(new ObjectMapper().writeValueAsString(value));
			} catch (JsonProcessingException e) {
				throw new BadRequestException("Invalid Value");
			}
			configRepo.save(config);
			logger.info("Configuration saved successfully for key : {}", key);
		}
		else {
			try {
				existingConfig.get().setData(new ObjectMapper().writeValueAsString(value));
			} catch (JsonProcessingException e) {
				throw new BadRequestException("Invalid Value");
			}
			configRepo.save(existingConfig.get());
			logger.info("Configuration saved successfully for key : {}", key);
		}
		return new StatusResponse(Constants.SUCCESS, HttpStatus.CREATED.value(), "Configuration Saved Successfully");
	}

	@Override
	public Object getConfiguration(String key) throws BadRequestException {
		logger.info("[SERVICE] Request received to get configuration for key : {}", key);
		if (key == null) {
			throw new BadRequestException("Please pass a valid key");
		}
		Optional<Config> config = configRepo.findByKeyName(key);
		if (config.isEmpty()) {
			throw new BadRequestException("No data found for config key");
		}

		Map<String, Object> data = null;
		try {
			data = new ObjectMapper().readValue(config.get().getData(), Map.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public StatusResponse mapRolesToPrivilege(List<RoleDto> roleData) throws BadRequestException {
		if (roleData == null || roleData.isEmpty()) {
			throw new BadRequestException("Invalid Input for Role");
		}

		List<Role> existingRoleData = roleRepo.findAllByName(roleData.stream().map(RoleDto::getName).toList());
		if (existingRoleData.isEmpty() || roleData.size() != existingRoleData.size()) {
			throw new BadRequestException("Role doesn't exists. Please add role before mapping");
		}

		for (RoleDto role : roleData) {
			for (Role existingRole : existingRoleData) {
				if (existingRole.getName().equalsIgnoreCase(role.getName())) {
					List<String> existingPrivilege = existingRole.getPrivileges().stream().map(Privilege::getName)
							.toList();
					for (PrivilegeDto privilege : role.getPrivileges()) {
						if (!existingPrivilege.contains(privilege.getName())) {
//							Privilege newPrivilege = modelMapper.convertValue(privilege, Privilege.class);
							Optional<Privilege> ePrivilege = privilegeRepository.findByName(privilege.getName());
							if (ePrivilege.isPresent()) {
								existingRole.getPrivileges().add(ePrivilege.get());
							}
						}
					}
				}
			}
		}
		roleRepo.saveAll(existingRoleData);
		return new StatusResponse(Constants.SUCCESS, HttpStatus.OK.value(), "Role(s) Mapped Successfully");
	}

	@Override
	public StatusResponse addRole(List<RoleDto> roleDto) {
		logger.info("Request received to add role");
		if (roleDto == null || roleDto.isEmpty()) {
			throw new BadRequestException("Invalid input for role data.");
		}

		List<Role> existingRoleData = roleRepo.findAllByName(roleDto.stream().map(RoleDto::getName).toList());
		if (!existingRoleData.isEmpty()) {
			throw new BadRequestException("Role(s) already exists. Duplication occurred");
		}

		List<Role> newRoles = roleDto.stream().map(this::convertRoleDtoToRole).toList();
		roleRepo.saveAll(newRoles);
		logger.info("New Role(s) saved successfully");
		return new StatusResponse(Constants.SUCCESS, HttpStatus.OK.value(), "New Role(s) Added Successfully");
	}

	private Role convertRoleDtoToRole(RoleDto roleDto) {
		return modelMapper.convertValue(roleDto, Role.class);
	}

	private RoleDto convertRoleToRoleDto(Role role) {
		RoleDto roleDto = new RoleDto();
		roleDto.setId(role.getId());
		roleDto.setName(role.getName());
		roleDto.setPrivileges(role.getPrivileges().stream().map(this::convertPrivilegeToPrivilegeDto).collect(Collectors.toSet()));
		return roleDto;
	}

	@Override
	public List<RoleDto> getAllRoles() {
		logger.info("Request to return all roles");
		List<Role> rolesList = roleRepo.findAll();
		return rolesList.stream().map(this::convertRoleToRoleDto).toList();
	}

	@Override
	public StatusResponse addPrivileges(List<PrivilegeDto> privilegeDto) {
		logger.info("Request received to add privilege");
		if (privilegeDto == null || privilegeDto.isEmpty()) {
			throw new BadRequestException("Invalid input for privilege data.");
		}

		List<Privilege> existingData = privilegeRepository
				.findAllByName(privilegeDto.stream().map(PrivilegeDto::getName).toList());
		if (!existingData.isEmpty()) {
			throw new BadRequestException("Privilege(s) already exists. Duplication occurred");
		}

		List<Privilege> newRoles = privilegeDto.stream().map(this::convertPrivilegeDtoToPrivilege).toList();
		privilegeRepository.saveAll(newRoles);
		logger.info("New Privilege(s) saved successfully");
		return new StatusResponse(Constants.SUCCESS, HttpStatus.OK.value(), "New Privilege(s) Added Successfully");
	}

	private Privilege convertPrivilegeDtoToPrivilege(PrivilegeDto privilegeDto) {
		return modelMapper.convertValue(privilegeDto, Privilege.class);
	}

	private PrivilegeDto convertPrivilegeToPrivilegeDto(Privilege privilege) {
		PrivilegeDto privilegeDto = new PrivilegeDto();
		privilegeDto.setId(privilege.getId());
		privilegeDto.setName(privilege.getName());
		privilegeDto.setEndpoints(privilege.getEndpoints().stream().map(this::convertEndpointToEndpointDto).collect(Collectors.toSet()));
		return privilegeDto;
	}

	@Override
	public List<PrivilegeDto> getAllPrivileges() {
		logger.info("Request to return all privileges");
		List<Privilege> privilegeList = privilegeRepository.findAll();
		return privilegeList.stream().map(this::convertPrivilegeToPrivilegeDto).toList();
	}

	@Override
	public StatusResponse addEndpoint(@Valid List<EndpointDto> endpointDto) {
		logger.info("Request received to add endpoints");
		if (endpointDto == null || endpointDto.isEmpty()) {
			throw new BadRequestException("Invalid input for endpoint data.");
		}

		List<Endpoint> existingData = endpointRepository
				.findAllByName(endpointDto.stream().map(EndpointDto::getName).toList());
		if (!existingData.isEmpty()) {
			throw new BadRequestException("Endpoint(s) already exists. Duplication occurred");
		}

		List<Endpoint> newRoles = endpointDto.stream().map(this::convertEndpointDtoToEndpoint).toList();
		endpointRepository.saveAll(newRoles);
		logger.info("New Endpoint(s) saved successfully");
		return new StatusResponse(Constants.SUCCESS, HttpStatus.OK.value(), "New Endpoint(s) Added Successfully");
	}

	private Endpoint convertEndpointDtoToEndpoint(EndpointDto endpointDto) {
		return modelMapper.convertValue(endpointDto, Endpoint.class);
	}

	private EndpointDto convertEndpointToEndpointDto(Endpoint endpoint) {
		EndpointDto endpointDto = new EndpointDto();
		endpointDto.setId(endpoint.getId());
		endpointDto.setName(endpoint.getName());
		endpointDto.setEndpointUri(endpoint.getEndpointUri());
		endpointDto.setMethod(endpoint.getMethod());
		endpointDto.setDescription(endpoint.getDescription());
		return modelMapper.convertValue(endpoint, EndpointDto.class);
	}

	@Override
	public List<EndpointDto> getAllEndpoints() {
		logger.info("Request to return all endpoints");
		List<Endpoint> endpointList = endpointRepository.findAll();
		return endpointList.stream().map(this::convertEndpointToEndpointDto).toList();
	}

	@Override
	public StatusResponse mapPrivilegeToEndpoint(List<PrivilegeDto> privileges) {
		if (privileges == null || privileges.isEmpty()) {
			throw new BadRequestException("Invalid Input for Privilege");
		}

		List<Privilege> existingData = privilegeRepository
				.findAllByName(privileges.stream().map(PrivilegeDto::getName).toList());
		if (existingData.isEmpty() || privileges.size() != existingData.size()) {
			throw new BadRequestException("Role doesn't exists. Please add role before mapping");
		}

		for (PrivilegeDto privilege : privileges) {
			for (Privilege existingPrivilege : existingData) {
				if (privilege.getName().equalsIgnoreCase(existingPrivilege.getName())) {
					for (EndpointDto endpoint : privilege.getEndpoints()) {
						Boolean isExist = Boolean.FALSE;
						for (Endpoint existingEndpoint : existingPrivilege.getEndpoints()) {
							if (existingEndpoint.getEndpointUri().equalsIgnoreCase(endpoint.getEndpointUri())
									&& existingEndpoint.getMethod().equalsIgnoreCase(endpoint.getMethod())) {
								isExist = Boolean.TRUE;
							}
						}

						if (Boolean.FALSE.equals(isExist)) {
							Endpoint newEndpoint = modelMapper.convertValue(endpoint, Endpoint.class);
							existingPrivilege.getEndpoints().add(newEndpoint);
						}
					}
				}
			}
		}
		privilegeRepository.saveAll(existingData);
		return new StatusResponse(Constants.SUCCESS, HttpStatus.OK.value(), "Privilege(s) Mapped Successfully");
	}

	@Override
	public List<RoleDto> getRolesData() {
		logger.info("Request to return all roles");
		List<Role> rolesList = roleRepo.findAll();
		return rolesList.stream().map(this::convertRoleDataToRoleDataDto).toList();
	}

	private RoleDto convertRoleDataToRoleDataDto(Role role) {
		RoleDto roleDto = new RoleDto();
		roleDto.setId(role.getId());
		roleDto.setName(role.getName());
		for (Privilege privilege : role.getPrivileges()) {
			PrivilegeDto privilegeDto = new PrivilegeDto();
			privilegeDto.setId(privilege.getId());
			privilegeDto.setName(privilege.getName());
			for (Endpoint endpoint : privilege.getEndpoints()) {
				EndpointDto endpointDto = new EndpointDto();
				endpointDto.setId(endpoint.getId());
				endpointDto.setName(endpoint.getName());
				endpointDto.setMethod(endpoint.getMethod());
				endpointDto.setEndpointUri(endpoint.getEndpointUri());
				endpointDto.setDescription(endpoint.getDescription());
				privilegeDto.getEndpoints().add(endpointDto);
			}
			roleDto.getPrivileges().add(privilegeDto);
		}
		return roleDto;
	}
}