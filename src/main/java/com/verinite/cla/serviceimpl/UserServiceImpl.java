package com.verinite.cla.serviceimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.verinite.cla.dto.UserDto;
import com.verinite.cla.model.Privilege;
import com.verinite.cla.model.Role;
import com.verinite.cla.model.User;
import com.verinite.cla.repository.UserRepository;
import com.verinite.cla.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

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

	public UserDto userToUserDto(User user) {

		UserDto userDto = new UserDto();

		userDto.setUsername(user.getUsername());
//		userDto.setPassword(user.getPassword());
		userDto.setEmail(user.getEmail());
		userDto.setId(user.getId());

		return userDto;

	}
}