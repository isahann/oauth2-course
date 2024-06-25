package com.appsdeveloperblog.ws.api.ResourceServer.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	@GetMapping("/status/check")
	public String status() {
		return "Hello OAuth2!";
	}

	@GetMapping("/cats")
	public List<String> customScopeTest() {
		return Arrays.asList("Joselito", "Gigio", "Bepinho", "Dimitri");
	}

	@GetMapping("/developers")
	public List<String> customRoleTest() {
		return Arrays.asList("Isahann", "Hanacleto");
	}
}
