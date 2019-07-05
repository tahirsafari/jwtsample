package com.jwtsample.jwtsample.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jwtsample.jwtsample.exceptions.ResourceNotFoundException;
import com.jwtsample.jwtsample.models.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String accessId) {
        // Let people login with either username or email
        User user = userRepository.findByAccessId(accessId)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with accessId : " + accessId)
        );

        return UserPrincipal.create(user);
	}

	@Transactional
	public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
            );

            return UserPrincipal.create(user);
	}

}
