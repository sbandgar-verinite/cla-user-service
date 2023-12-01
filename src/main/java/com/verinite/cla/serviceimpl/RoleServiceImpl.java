package com.verinite.cla.serviceimpl;

import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.verinite.cla.dto.PrivilegeDto;
import com.verinite.cla.dto.RoleDto;
import com.verinite.cla.model.Privilege;
import com.verinite.cla.model.Role;
import com.verinite.cla.repository.RoleRepository;
import com.verinite.cla.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private ObjectMapper modelMapper;

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