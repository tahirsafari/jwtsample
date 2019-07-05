package com.jwtsample.jwtsample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jwtsample.jwtsample.models.Role;
import com.jwtsample.jwtsample.models.RoleName;
import com.jwtsample.jwtsample.services.AuthenticationService;
import com.safari.pg.cbs.def._UserAuthInfo;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

	@Autowired
	AuthenticationService authenticationService;
    public CustomAuthenticationProvider() {
        super();
    }
    
	@Override
	public Authentication authenticate(Authentication authentication) {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
	     System.out.println("password "+password);
	     _UserAuthInfo userInfo;
		try {
			userInfo = authenticationService.authenticate(name, password);
//	        if ( userInfo != null) {
//	        	 System.out.println("authenticated ");
//	            //final Set<Role> grantedAuths = new HashSet<>();
	            final List<GrantedAuthority> grantedAuths = new ArrayList<>();
	            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
	            final UserDetails principal = new User(name, password, grantedAuths);
	            final Authentication auth = new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);
	            return auth;
//	        } else {
//	        	throw new BadCredentialsException("Authentication failed");
//	        }
		} catch (Exception e) {
			throw new BadCredentialsException("Authentication failed");
		}

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
//	public boolean letsAuthenticate(String name, String password) {
//		System.out.println("data "+name);
////		if(name.equals("admin") && password.equals("admin1"))
////			return true;
////		return false;
//		
//	}

}
