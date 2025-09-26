package com.bzf.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bzf.authservice.dto.LoginForm;
import com.bzf.authservice.entity.Users;
import com.bzf.authservice.repository.UsersRepository;
import com.bzf.authservice.service.JwtService;
import com.bzf.authservice.service.UsersService;

@RestController
@RequestMapping("/auth")
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UsersService userService;

	private final PasswordEncoder passwordEncoder;

	LoginController(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Autowired
	private UsersRepository userRepository;

	public void RegisterUsersController(UsersRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@GetMapping("/user/home")
	public String handleUserHome() {
		return "user_home";
	}

	@GetMapping("/admin/home")
	public String handleAdminHome() {
		return "admin_home";
	}

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@PostMapping("/register/user")
	public Users createUser(@RequestBody Users user) {
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			throw new IllegalArgumentException("Password do not match");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userService.save(user);
	}

	@PostMapping("/confirm")
	public String confirmRegisteration(@RequestParam("token") String token) {

		Users user = userService.findByConfirmationToken(token);
		if (user == null) {
			return "Invalid Token";
		}

		boolean activateUser = userService.activateUser(token, user);
		if (activateUser == false) {
			userService.delete(token);
			return "Your Token is expired. Please re-try!";
		}

		return "Account activated";
	}

	@PostMapping("/authenticate")
	public String authenticateAndGetToken(@RequestBody LoginForm loginForm) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginForm.username(), loginForm.password()));
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(userService.loadUserByUsername(loginForm.username()));
		} else {
			throw new UsernameNotFoundException("Invalid credentials");
		}
	}

}
