package com.verinite.cla.service;

import org.apache.coyote.BadRequestException;

import com.verinite.cla.dto.RoleDto;

public interface RoleService {

	String mapRolesToPrivilege(RoleDto role) throws BadRequestException;

}
