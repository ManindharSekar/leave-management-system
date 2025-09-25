package com.bzf.ui_service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class LoginUiController {
	
	
	
	private final RestTemplate restTemplate;
    private final String loginServiceUrl;

    public LoginUiController(RestTemplate restTemplate,
                               @Value("${services.login.base-url}") String loginServiceUrl) {
        this.restTemplate = restTemplate;
        this.loginServiceUrl = loginServiceUrl;
    }
	@GetMapping("/login")
	public String loginPage(Model model) {
		String forObject = restTemplate.getForObject(loginServiceUrl+"/login", String.class);
		model.addAttribute(forObject);
		return "loginpage/login";
	}

}
