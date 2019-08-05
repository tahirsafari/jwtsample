package com.jwtsample;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider{

	private CustomAuthenticationProvider authProvider;
	public CustomUserDetailsAuthenticationProvider() {
		super();
	}
	public CustomUserDetailsAuthenticationProvider(PasswordEncoder passwordEncoder,
			CustomAuthenticationProvider authProvider) {
		this.authProvider = authProvider;
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(
                messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }

        String presentedPassword = authentication.getCredentials()
            .toString();

//        if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
//            logger.debug("Authentication failed: password does not match stored value");
//            throw new BadCredentialsException(
//                messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
//        }
		
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
        CustomAuthenticationToken auth = (CustomAuthenticationToken) authentication;
        UserDetails loadedUser;
        
        return (UserDetails)authProvider.authenticate(auth).getPrincipal();
		//return null;
	}

}
