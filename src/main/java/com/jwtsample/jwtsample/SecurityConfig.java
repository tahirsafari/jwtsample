package com.jwtsample.jwtsample;

//import com.jwtsample.jwtsample.services.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jwtsample.jwtsample.services.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(
//        securedEnabled = true,
//        jsr250Enabled = true,
//        prePostEnabled = true
//)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private CustomAuthenticationProvider authProvider;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
    
    public SecurityConfig() {
        super();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
		System.out.println("Authenticate configure ");
		auth.authenticationProvider(authProvider);

//        auth.inMemoryAuthentication()
//            .withUser("admin")
//            .password("admin1")
//            //{noop} makes sure that the password encoder doesn't do anything
//            .roles("USER") // Role of the user
//            .and()
//            .withUser("user")
//            .password("admin1")
//            .credentialsExpired(false)
//            .accountExpired(false)
//            .accountLocked(false)
//            .roles("ADMIN");
	}

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @SuppressWarnings("deprecation")
//    @Bean
//    public static NoOpPasswordEncoder passwordEncoder() {
//    	return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                .csrf()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(unauthorizedHandler)
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .authorizeRequests()
                    .antMatchers("/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                        .permitAll()
                    .antMatchers("/api/auth/**")
                        .permitAll()
                    .antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability")
                        .permitAll()
//                    .antMatchers(HttpMethod.GET, "/post/**", "/user/**")
//                        .permitAll()
                    .anyRequest()
                        .authenticated();

        // Add our custom JWT security filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}