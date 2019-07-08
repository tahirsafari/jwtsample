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
import com.jwtsample.jwtsample.models.Role;
import com.jwtsample.jwtsample.models.RoleName;
import com.jwtsample.jwtsample.services.AuthenticationService;
import com.jwtsample.jwtsample.services.UserPrincipal;
import com.jwtsample.jwtsample.services.UserRepository;
import com.safari.pg.cbs.def._UserAuthInfo;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider,UserDetailsService{

    @Autowired
    UserRepository userRepository;
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
	     UserDetails userInfo;
		try {
			userInfo = letsAuthenticate(name, password);//authenticationService.authenticate(name, password);
//	        if ( userInfo != null) {
//	        	 System.out.println("authenticated ");
	          //final Set<Role> grantedAuths = new HashSet<>();
	          //final List<GrantedAuthority> grantedAuths = new ArrayList<>();
//	          grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
//	          grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//	            grantedAuths.add(new Role(RoleName.ROLE_USER));
//	            System.out.println("password2 "+password);
//	           final UserDetails principal = (UserDetails) new com.jwtsample.jwtsample.models.User(name,1, password, grantedAuths);
//	           System.out.println("principal "+principal); 
//	           final Authentication auth = new UsernamePasswordAuthenticationToken(principal, password, new ArrayList<GrantedAuthority>(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
//	           System.out.println("auth "+auth); 
//	            return auth;
//	        } else {
//	        	throw new BadCredentialsException("Authentication failed");
//	        }
            final List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
            final UserDetails principal = new User(name, password, grantedAuths);
            final Authentication auth = new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);
            return auth;
		} catch (Exception e) {
			throw new BadCredentialsException("Authentication failed");
		}

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
		//return authentication.equals(UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
	public UserDetails letsAuthenticate(String name, String password) {
		System.out.println("data "+name);
        com.jwtsample.jwtsample.models.User user = userRepository.findByAccessId(name)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with accessId : " + name)
        );
        System.out.println("data "+user.getChannelId()+ " "+user.getAccessId() );
        return UserPrincipal.create(user);
		
	}
	
	@Transactional
	public UserDetails loadUserById(Long id) {
        com.jwtsample.jwtsample.models.User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
            );

            return UserPrincipal.create(user);
	}
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String accessId) {
        // Let people login with either username or email
		com.jwtsample.jwtsample.models.User user = userRepository.findByAccessId(accessId)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with accessId : " + accessId)
        );

        return UserPrincipal.create(user);
	}


}
