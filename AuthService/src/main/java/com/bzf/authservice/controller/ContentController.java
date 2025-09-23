package com.bzf.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentController {

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

}
