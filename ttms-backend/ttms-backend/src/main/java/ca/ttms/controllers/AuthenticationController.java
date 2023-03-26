package ca.ttms.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.ttms.beans.Person;
import ca.ttms.beans.details.UserAuthenticationDetails;
import ca.ttms.beans.details.UserRegisterDetails;
import ca.ttms.beans.response.ResponseMealUpdate;
import ca.ttms.beans.response.ResponseToken;
import ca.ttms.services.AuthenticationService;
import ca.ttms.services.JWTService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	private final AuthenticationService service;

	@PostMapping("/authenticate")
	public ResponseEntity<ResponseToken> authenticate(@RequestBody UserAuthenticationDetails authDetails) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return ResponseEntity.ok().headers(headers).body(service.authenticateUser(authDetails));
	}
}
