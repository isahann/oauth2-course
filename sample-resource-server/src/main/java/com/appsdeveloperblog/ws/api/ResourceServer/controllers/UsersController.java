package com.appsdeveloperblog.ws.api.ResourceServer.controllers;

import com.appsdeveloperblog.ws.api.ResourceServer.response.UserDetailsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UsersController {

	@Secured("hasAuthority('SCOPE_profile")
	@GetMapping("/status/check")
	public String status() {
		return "Hello OAuth2!";
	}

	@Secured("hasAuthority('SCOPE_cats")
	@GetMapping("/cats")
	public List<String> customScopeTest() {
		return Arrays.asList("Joselito", "Gigio", "Bepinho", "Dimitri");
	}

	@Secured("hasRole('developer')")
	@GetMapping("/developers")
	public List<String> customRoleTest() {
		return Arrays.asList("Isahann", "Hanacleto");
	}

	@PreAuthorize("hasRole('developer') or #id == #jwt.subject")
	@DeleteMapping(path = "/{id}")
	public String deleteUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
		return String.format("Deleted user with id [%s], JWT [%s]", id, jwt.getSubject());
	}

	@PostAuthorize("returnObject.userId == #jwt.subject")
	@GetMapping(path = "/{id}")
	public UserDetailsResponse getUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
		log.info("getUser() called");

		return UserDetailsResponse.builder()
				.userFirstName("Isahann")
				.userLastName("Hanacleto")
				.userId(id)
				.build();
	}
}
