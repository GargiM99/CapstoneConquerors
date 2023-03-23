package ca.ttms.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.ttms.beans.Person;
import ca.ttms.beans.details.UserAuthenticationDetails;
import ca.ttms.beans.details.UserRegisterDetails;
import ca.ttms.beans.response.ResponseMealUpdate;
import ca.ttms.beans.response.ResponseToken;
import ca.ttms.services.AuthenticationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	private final AuthenticationService service;

//	@PostMapping("/register")
//	public ResponseEntity<ResponseToken> register(@RequestBody UserRegisterDetails registerDetails) {
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		return ResponseEntity.ok().headers(headers).body(service.registerUser(registerDetails));
//	}
	
	@PostMapping("/register")
	public ResponseEntity<UserAuthenticationDetails> regstier(@RequestBody UserRegisterDetails registerDetails){
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Person person = registerDetails.getPerson();
		
		UserAuthenticationDetails details = service.registerUser(registerDetails);
		
		if (details == null)
			return ResponseEntity.status(400).headers(headers).body(null);

		return ResponseEntity.ok().headers(headers).body(details);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<ResponseToken> authenticate(@RequestBody UserAuthenticationDetails authDetails) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return ResponseEntity.ok().headers(headers).body(service.authenticateUser(authDetails));
	}
}
