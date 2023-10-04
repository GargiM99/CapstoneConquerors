package ca.ttms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.ttms.beans.details.UserAuthenticationDetails;
import ca.ttms.beans.details.UserRegisterDetails;
import ca.ttms.beans.response.BasicClientResponse;
import ca.ttms.services.AgentService;
import ca.ttms.services.AuthenticationService;
import ca.ttms.services.ClientService;
import ca.ttms.services.JWTService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {
	
	@Autowired
	private ClientService service;
	private final JWTService jwtService;
	private final AuthenticationService authService;
	
	@PostMapping()
	public ResponseEntity<BasicClientResponse> regstier(
			@RequestHeader("Authorization") String authHeader,
			@RequestBody UserRegisterDetails registerDetails){
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String jwtoken = authHeader.substring(7);
		String role = jwtService.extractAllClaims(jwtoken).get("role", String.class);
		String username = jwtService.extractSubject(jwtoken);
		
		if (role == null || !(role.equals("ADMIN") || role.equals("AGENT")))
			return ResponseEntity.status(401).headers(headers).body(null);
		
		BasicClientResponse response = service.registerClient(registerDetails, username);
		
		if (response == null)
			return ResponseEntity.status(400).headers(headers).body(null);
		
		return ResponseEntity.ok().headers(headers).body(response);
	}
}
