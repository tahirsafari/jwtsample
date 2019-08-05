package com.jwtsample.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.jwtsample.CustomAuthenticationToken;
import com.jwtsample.JwtAuthenticationResponse;
import com.jwtsample.JwtTokenProvider;
import com.jwtsample.LoginRequest;
import com.jwtsample.models.UserAuthInfo;
import com.safari.pg.cbs.def._UserAuthInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@Profile("!test")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
	@Value("${pghost.url}")
	private String pgHostUrl;
	ObjectMapper mapper = new ObjectMapper();

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    	System.out.println("Authenticate user "+loginRequest.toString());
        Authentication authentication = authenticationManager.authenticate(
                new CustomAuthenticationToken(
                        loginRequest.getAccessId(),
                        loginRequest.getPassword(),
                        loginRequest.getChannelId()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        System.out.println("Results "+authentication.getName()+" principal "+authentication.getPrincipal().toString());
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, authentication.getPrincipal()));
    }

	@PostMapping("/authenticate")
	public ResponseEntity<?> hitPGAPI(@RequestBody String request) throws IOException {
	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.postForObject(pgHostUrl, request, String.class);
	    System.out.println("result "+result);
	    JsonNode outputObj = mapper.readTree(result).path("payLoad");
	    _UserAuthInfo userInfo= mapper.convertValue(outputObj, _UserAuthInfo.class);
        final List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        final UserDetails principal = new UserAuthInfo(userInfo, "", grantedAuths);
        final Authentication authentication = new UsernamePasswordAuthenticationToken(principal, "", grantedAuths);
        String jwt = tokenProvider.generateToken(authentication);
        System.out.println("Results "+authentication.getName()+" principal "+authentication.getPrincipal().toString());
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, authentication.getPrincipal()));
	    
//	    //outputObj.findParent(fieldName)
//	    System.out.println("outputObj.findParent(\"payLoad\") "+outputObj.path("payLoad"));
//	    String payLoad = outputObj.findParent("payLoad").asText();
//	    System.out.println("payLoad "+payLoad);
//	    //outputObj.get("payLoad").findValue(fieldName)
//	    //String payLoad = outputObj.get("error").asText();
//	    System.out.println("payLoad "+payLoad);
//	    _UserAuthInfo userInfo= mapper.readValue(outputObj.path("payLoad"), new TypeReference<_UserAuthInfo>(){});
		//return userInfo;
		
	}

//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
////        if(userRepository.existsByAccessId(signUpRequest.getAccessId())) {
////            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
////                    HttpStatus.BAD_REQUEST);
////        }
//
//
//        // Creating user's account
//        User user = new User();
//
////       user.setPassword(passwordEncoder.encode(user.getPassword()));
////        user.setPassword(user.getPassword());
//
//        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
//                .orElseThrow(() -> new AppException("User Role not set."));
//
//       // user.setRoles(Collections.singleton(userRole));
//
//        User result = userRepository.save(user);
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/users/{username}")
//                .buildAndExpand(result.getUserId()).toUri();
//
//        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
//    }
}