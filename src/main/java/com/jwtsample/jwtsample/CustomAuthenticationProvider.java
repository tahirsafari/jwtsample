package com.jwtsample.jwtsample;

import java.util.ArrayList;
import java.util.Arrays;
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
//import com.jwtsample.jwtsample.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User;
import com.jwtsample.jwtsample.exceptions.ResourceNotFoundException;
//import com.jwtsample.jwtsample.models.Role;
//import com.jwtsample.jwtsample.models.RoleName;
import com.jwtsample.jwtsample.models.UserAuthInfo;
import com.jwtsample.jwtsample.services.AuthenticationService;
import com.jwtsample.jwtsample.services.UserPrincipal;
//import com.jwtsample.jwtsample.services.UserPrincipal;
//import com.jwtsample.jwtsample.services.UserRepository;
import com.safari.pg.cbs.def._UserAuthInfo;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider,UserDetailsService{

//    @Autowired
//    UserRepository userRepository;
	@Autowired
	AuthenticationService authenticationService;
    public CustomAuthenticationProvider() {
        super();
    }
    
	@Override
	public Authentication authenticate(Authentication authentication) {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        CustomAuthenticationToken auth = (CustomAuthenticationToken) authentication;
        int channelId = Integer.parseInt(auth.getChannelId());

	     System.out.println("password "+password);
	     _UserAuthInfo userInfo;
		try {
			userInfo = authenticationService.authenticate(name, password, channelId);
            final List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
            final UserDetails principal = new UserAuthInfo(userInfo, password, grantedAuths);
            final Authentication auth2 = new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);
            return auth2;
		} catch (Exception e) {
			throw new BadCredentialsException("Authentication failed");
		}

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
		//return authentication.equals(UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
//	public UserDetails letsAuthenticate(String name, String password) {
//		System.out.println("data "+name);
//        com.jwtsample.jwtsample.models.User user = userRepository.findByAccessId(name)
//                .orElseThrow(() ->
//                        new UsernameNotFoundException("User not found with accessId : " + name)
//        );
//       // System.out.println("data "+user.getChannelId()+ " "+user.getAccessId() );
//        return UserPrincipal.create(user);
//		
//	}
	
	@Transactional
	public UserDetails loadUserById(Long id) throws Exception {
//        com.jwtsample.jwtsample.models.User user = userRepository.findByUserId(id).orElseThrow(
//                () -> new ResourceNotFoundException("User", "id", id)
//            );
//
//            return UserPrincipal.create(user);
		_UserAuthInfo userInfo = authenticationService.getUserInfoByUserId(id);
		
		return UserPrincipal.createUserAuthInfo(userInfo);
	}
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String accessId) {
        // Let people login with either username or email
//		com.jwtsample.jwtsample.models.User user = userRepository.findByUserId(0L)
//                .orElseThrow(() ->
//                        new UsernameNotFoundException("User not found with accessId : " + accessId)
//        );
//
//        return UserPrincipal.create(user);
		return null;
	}


}
