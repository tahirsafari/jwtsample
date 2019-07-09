package com.jwtsample.jwtsample.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jwtsample.jwtsample.ApiResponse;
import com.jwtsample.jwtsample.JwtAuthenticationResponse;
import com.jwtsample.jwtsample.JwtTokenProvider;
import com.jwtsample.jwtsample.LoginRequest;
import com.jwtsample.jwtsample.SignUpRequest;
import com.jwtsample.jwtsample.models.Role;
import com.jwtsample.jwtsample.models.RoleName;
import com.jwtsample.jwtsample.models.User;
import com.jwtsample.jwtsample.services.RoleRepository;
import com.jwtsample.jwtsample.services.UserRepository;
import com.jwtsample.jwtsample.exceptions.AppException;
import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    	System.out.println("Authenticate user "+loginRequest.toString());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getAccessId(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        System.out.println("Results "+authentication.getName()+" principal "+authentication.getPrincipal().toString());
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, authentication.getPrincipal()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
//        if(userRepository.existsByAccessId(signUpRequest.getAccessId())) {
//            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
//                    HttpStatus.BAD_REQUEST);
//        }


        // Creating user's account
        User user = new User();

//       user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setPassword(user.getPassword());

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

       // user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUserId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}