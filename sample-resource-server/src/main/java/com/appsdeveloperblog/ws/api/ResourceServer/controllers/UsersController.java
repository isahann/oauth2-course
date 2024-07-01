package com.appsdeveloperblog.ws.api.ResourceServer.controllers;

import com.appsdeveloperblog.ws.api.ResourceServer.response.UserDetailsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
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
@RequiredArgsConstructor
@Slf4j
public class UsersController {

	private final Environment env;

	// either can be used
//	@Secured("SCOPE_profile")
	@PreAuthorize("hasAuthority('SCOPE_cats')")
	@GetMapping("/status/check")
	public String status() {
		return String.format("Hello OAuth2! Running on port [%s]", env.getProperty("local.server.port"));
	}

//	@Secured("SCOPE_cats")
	@PreAuthorize("hasAuthority('SCOPE_cats')")
	@GetMapping("/cats")
	public List<String> customScopeTest() {
		return Arrays.asList("Joselito", "Gigio", "Bepinho", "Dimitri");
	}

//	@Secured("ROLE_developer")
	@PreAuthorize("hasRole('developer')")
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
		return UserDetailsResponse.builder()
				.userFirstName("Isahann")
				.userLastName("Hanacleto")
				.userId(id)
				.build();
	}
}
