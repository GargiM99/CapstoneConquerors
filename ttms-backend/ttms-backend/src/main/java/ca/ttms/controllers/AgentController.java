package ca.ttms.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.ttms.beans.Person;
import ca.ttms.beans.details.BasicUsersDetails;
import ca.ttms.beans.details.MealPriceDetails;
import ca.ttms.beans.details.UserAuthenticationDetails;
import ca.ttms.beans.details.UserRegisterDetails;
import ca.ttms.services.AgentService;
import ca.ttms.services.AuthenticationService;
import ca.ttms.services.JWTService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/agent")
@RequiredArgsConstructor
public class AgentController {

	@Autowired
	private AgentService service;
	private final JWTService jwtService;
	private final AuthenticationService authService;

	@GetMapping()
	public Map<String, Object>[] getMeals() {
		return service.getAgents();
	}

	@PostMapping()
	public ResponseEntity<UserAuthenticationDetails> regstier(
			@RequestHeader("Authorization") String authHeader,
			@RequestBody UserRegisterDetails registerDetails) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Person person = registerDetails.getPerson();

		String jwtoken = authHeader.substring(7);
		String role = jwtService.extractAllClaims(jwtoken).get("Role", String.class);
		
		if (role == null || !role.equals("ADMIN"))
			return ResponseEntity.status(401).headers(headers).body(null);
		
		UserAuthenticationDetails details = authService.registerUser(registerDetails);

		if (details == null)
			return ResponseEntity.status(400).headers(headers).body(null);

		return ResponseEntity.ok().headers(headers).body(details);
	}
}
