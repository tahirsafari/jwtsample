package com.jwtsample.jwtsample.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface IUserDetailsService {
	public UserDetails loadUserByAccessId(String accessId);
	public UserDetails loadUserById(Long id);
}
