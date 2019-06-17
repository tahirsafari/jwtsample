package com.jwtsample.jwtsample.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface IUserDetailsService {
	public UserDetails loadUserByUsername(String usernameOrEmail);
	public UserDetails loadUserById(Long id);
}
