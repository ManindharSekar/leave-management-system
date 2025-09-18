package com.bzf.authservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.authorizeHttpRequests(registry ->{
			registry.requestMatchers("/home").permitAll();
			registry.requestMatchers("/user/**").hasRole("USER");
			registry.requestMatchers("/admin/**").hasRole("ADMIN");
		})
				.formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
				.build();
	}
	
	@Bean
	public UserDetailsService userDetailService() {
		UserDetails normalUser=User.builder()
				.username("mani")
				.password("$2a$12$/Osi9TGIr1MpSvTQ7rDtTupJZBZPbHwIBx.6KtJ4p9LQphW7FaDfm")
				.roles("USER")
				.build();
		
		UserDetails adminUser=User.builder()
				.username("sekar")
				.password("$2a$12$BDdgXaV.21XjdM9IRGMvq.Fanw2g9SOzNpF0PnixEKXsx.xueQLgu")
				.roles("ADMIN","USER")
				.build();				
				return new InMemoryUserDetailsManager(normalUser,adminUser);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
