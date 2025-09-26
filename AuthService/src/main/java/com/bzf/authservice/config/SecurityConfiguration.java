package com.bzf.authservice.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bzf.authservice.service.UsersService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	public UsersService userService;
	
	 @Autowired
	    private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	
	public void setUserService(UsersService userService) {
		this.userService = userService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	    return httpSecurity
	            .csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(registry -> {
	                registry.requestMatchers("/auth/register/**","/auth/confirm/**","/swagger-ui/**","/auth/authenticate").permitAll();
	                registry.requestMatchers("/user/**").hasRole("USER");
	                registry.requestMatchers("/admin/**").hasRole("ADMIN");
	            })
	            .formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer
                            .loginPage("/auth/login")
                            .successHandler(new AuthenticationSuccessHandler())
                            .permitAll();
                })
	            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
	            .oauth2Login(Customizer.withDefaults())
	            
	            .build();
	}

	
//	@Bean
//	public UserDetailsService userDetailService() {
//		UserDetails normalUser=User.builder()
//				.username("mani")
//				.password("$2a$12$/Osi9TGIr1MpSvTQ7rDtTupJZBZPbHwIBx.6KtJ4p9LQphW7FaDfm")
//				.roles("USER")
//				.build();
//		
//		UserDetails adminUser=User.builder()
//				.username("sekar")
//				.password("$2a$12$BDdgXaV.21XjdM9IRGMvq.Fanw2g9SOzNpF0PnixEKXsx.xueQLgu")
//				.roles("ADMIN","USER")
//				.build();				
//				return new InMemoryUserDetailsManager(normalUser,adminUser);
//	}
	
	@Bean
	public UserDetailsService userDetailService() {
		return userService;
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	 @Bean
	    public AuthenticationManager authenticationManager() {
	        return new ProviderManager(authenticationProvider());
	    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
