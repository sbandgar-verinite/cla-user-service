package com.verinite.cla.serviceimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.verinite.cla.dto.ApplicationDto;
import com.verinite.cla.dto.UserDto;
import com.verinite.cla.model.Application;
import com.verinite.cla.model.Privilege;
import com.verinite.cla.model.Role;
import com.verinite.cla.model.Tenant;
import com.verinite.cla.model.User;
import com.verinite.cla.repository.ApplicationRepository;
import com.verinite.cla.repository.TenantRepository;
import com.verinite.cla.repository.UserRepository;
import com.verinite.cla.service.UserService;
import com.verinite.commons.controlleradvice.BadRequestException;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TenantRepository tenantRepo;

	@Autowired
	private ApplicationRepository applicationRepo;

	@Override
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) {
				User user = userRepository.findByEmail(username)
						.orElseThrow(() -> new UsernameNotFoundException("User not found"));
				return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
						user.isEnabled(), true, true, true, getAuthorities(user.getRoles()));
			}
		};
	}

	private Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
		return getGrantedAuthorities(getPrivileges(roles));
	}

	private List<String> getPrivileges(final Collection<Role> roles) {
		final List<String> privileges = new ArrayList<>();
		final List<Privilege> collection = new ArrayList<>();
		for (final Role role : roles) {
			privileges.add(role.getName());
			collection.addAll(role.getPrivileges());
		}
		for (final Privilege item : collection) {
			privileges.add(item.getName());
		}

		return privileges;
	}

	private List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
		final List<GrantedAuthority> authorities = new ArrayList<>();
		for (final String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

	@Override
	public List<UserDto> getAllUser() {

		List<User> userList = userRepository.findAll();

		List<UserDto> userDto = new ArrayList<>();

		if (userList != null) {
			userDto = userList.stream().map(user -> userToUserDto(user)).collect(Collectors.toList());
		}

		return userDto;

	}

	@Override
	public UserDto getUserDetails(String email) {

		if (StringUtils.isBlank(email)) {
			throw new BadRequestException(" email is empty: " + email);
		}
		Optional<User> userEmail = userRepository.findByEmail(email);

		if (userEmail.isEmpty()) {
			throw new BadRequestException("User Data Not Found for email id : " + email);
		}

		List<Tenant> tenantList = tenantRepo.findByUser(userEmail.get().getEmail());
		List<Application> applicationList = applicationRepo
				.findByTenantId(tenantList.stream().map(Tenant::getId).toList());

		UserDto userDto = userToUserDto(userEmail.get());
		if (!CollectionUtils.isEmpty(applicationList)) {
			List<ApplicationDto> applicationDto = new ArrayList<>();
			for (Application application : applicationList) {
				ApplicationDto applicationDtoData = new ApplicationDto();
				applicationDtoData.setApplicationName(application.getApplicationName());
				applicationDtoData.setApplicationNumber(application.getApplicationNumber());
				applicationDtoData.setId(application.getId());
				applicationDtoData.setStatus(application.getStatus());
				if (!CollectionUtils.isEmpty(application.getTenant())) {
					for (Tenant tenant : application.getTenant()) {
						if (tenantList.contains(tenant)) {
							applicationDto.add(applicationDtoData);
						}
					}
				}
			}
			userDto.setApplications(applicationDto);
		}

		return userDto;
	}

	public UserDto userToUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setUsername(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setId(user.getId());
		return userDto;
	}

	@Override
	public User findByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}

}