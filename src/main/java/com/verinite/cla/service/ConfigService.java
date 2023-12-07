package com.verinite.cla.service;

import java.util.List;

import com.verinite.cla.controlleradvice.BadRequestException;
import com.verinite.cla.dto.EndpointDto;
import com.verinite.cla.dto.PrivilegeDto;
import com.verinite.cla.dto.RoleDto;
import com.verinite.cla.dto.StatusResponse;

import jakarta.validation.Valid;

public interface ConfigService {

	StatusResponse addConfiguration(String key, Object value) throws BadRequestException;

	Object getConfiguration(String key) throws BadRequestException;

	StatusResponse mapRolesToPrivilege(@Valid List<RoleDto> role) throws BadRequestException;

	StatusResponse addRole(@Valid List<RoleDto> roleDto);

	List<RoleDto> getAllRoles();

	StatusResponse addPrivileges(@Valid List<PrivilegeDto> privilegeDto);

	List<PrivilegeDto> getAllPrivileges();

	StatusResponse addEndpoint(@Valid List<EndpointDto> endpointDto);

	List<EndpointDto> getAllEndpoints();

	StatusResponse mapPrivilegeToEndpoint(List<PrivilegeDto> privilege);

	List<RoleDto> getRolesData();

}
