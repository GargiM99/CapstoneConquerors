package ca.ttms.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.ttms.beans.UserAuthenticationDetails;
import ca.ttms.beans.UserRegisterDetails;
import ca.ttms.services.AuthenticationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	private final AuthenticationService service;

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserRegisterDetails registerDetails) {
		System.out.println(registerDetails.toString());
		return ResponseEntity.ok(service.registerUser(registerDetails));
	}

	@PostMapping("/authenticate")
	public ResponseEntity<String> authenticate(@RequestBody UserAuthenticationDetails authDetails) {
		return ResponseEntity.ok(service.authenticateUser(authDetails));
	}
}
