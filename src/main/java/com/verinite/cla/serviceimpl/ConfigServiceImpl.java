package com.verinite.cla.serviceimpl;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.verinite.cla.controlleradvice.BadRequestException;
import com.verinite.cla.dto.PrivilegeDto;
import com.verinite.cla.dto.RoleDto;
import com.verinite.cla.dto.StatusResponse;
import com.verinite.cla.model.Config;
import com.verinite.cla.model.Privilege;
import com.verinite.cla.model.Role;
import com.verinite.cla.repository.ConfigRepository;
import com.verinite.cla.repository.RoleRepository;
import com.verinite.cla.service.ConfigService;
import com.verinite.cla.util.Constants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConfigServiceImpl implements ConfigService {

	private final Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);

	@Autowired
	private ConfigRepository configRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private ObjectMapper modelMapper;

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
		if (existingConfig.isPresent()) {
			throw new BadRequestException("Duplicate Key. Key already exists");
		}

		Config config = new Config();
		config.setKeyName(key);
		try {
			config.setData(new ObjectMapper().writeValueAsString(value));
		} catch (JsonProcessingException e) {
			throw new BadRequestException("Invalid Value");
		}
		configRepo.save(config);
		logger.info("Configuration saved successfully for key : {}", key);
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
	public String mapRolesToPrivilege(RoleDto role) throws BadRequestException {
		if (role == null || role.getName() == null) {
			throw new BadRequestException("Invalid Input for Role");
		}

		Optional<Role> existingRoleData = roleRepo.findByName(role.getName());
		if (existingRoleData.isEmpty()) {
			throw new BadRequestException("Role doesn't exists. Please add role before mapping");
		}

		if (!CollectionUtils.isEmpty(role.getPrivileges())) {
			for (PrivilegeDto privilege : role.getPrivileges()) {
				Privilege newPrivilege = modelMapper.convertValue(privilege, Privilege.class);
				existingRoleData.get().getPrivileges().add(newPrivilege);
			}
			roleRepo.save(existingRoleData.get());
		}

		return null;
	}
}