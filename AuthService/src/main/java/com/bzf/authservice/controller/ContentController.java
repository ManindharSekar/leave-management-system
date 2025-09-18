package com.bzf.authservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/")
public class ContentController {
	
	@GetMapping("/home")
	public String handleWelcome() {
		return "home";
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
    public String login() {
        return "custom_login";
    }

}
