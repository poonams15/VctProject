package com.velocity.eShopCart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.velocity.eShopCart.filter.JwtAuthFilter;
import com.velocity.eShopCart.service.impl.UserInfoUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class UserInfoConfig {

	//Authentication
	@Autowired
	private JwtAuthFilter authFilter;
	
	@Bean
	public UserDetailsService userDetailsService() {
		
		//load from db
		
		return new UserInfoUserDetailsService();
		
	}

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	//Authorization
	
	

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		return http.csrf().disable()
					.authorizeHttpRequests()
					.requestMatchers("/user/add",
							"/user/authenticate",
							"/subcategory/**", 
							"/category/**",
							"/product/**",
							"/file/**",
							"/ProductPrice/**")
					.permitAll()
					.and()
					.authorizeHttpRequests()
					.requestMatchers("/user/**").authenticated()
					.and()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
					.authenticationProvider(authenticationProvider())
					.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class).build();
		
		
	}
	
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
		return  daoAuthenticationProvider;
		
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		
		return config.getAuthenticationManager();
	}
	
}
//code
