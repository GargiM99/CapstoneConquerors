package ca.ttms.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.ttms.beans.Person;
import ca.ttms.beans.details.UserAuthenticationDetails;
import ca.ttms.beans.details.UserEditDetails;
import ca.ttms.beans.details.UserFullDetails;
import ca.ttms.beans.details.UserPromoteDetails;
import ca.ttms.beans.details.UserRegisterDetails;
import ca.ttms.beans.enums.Roles;
import ca.ttms.beans.response.PromoteAgentResponse;
import ca.ttms.beans.response.ResetPasswordResponse;
import ca.ttms.services.AgentService;
import ca.ttms.services.AuthenticationService;
import ca.ttms.services.JWTService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/agent")
@RequiredArgsConstructor
/**
 * REST controller for the agent 
 * doesam, 5lmjNhGXBw5v
 * @author Hamza
 * date: 2023/03/07 
 */
public class AgentController {

	@Autowired
	private AgentService service;
	private final JWTService jwtService;
	private final AuthenticationService authService;

	@GetMapping()
	public Map<String, Object>[] getAgents() {
		return service.getAgents();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserFullDetails> getAgentDetails(@PathVariable Integer id,
			@RequestHeader("Authorization") String authHeader) {
		
		String jwtoken = authHeader.substring(7);
		String role = jwtService.extractAllClaims(jwtoken).get("role", String.class);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		if (role == null || !role.equals("ADMIN"))
			return ResponseEntity.status(401).headers(headers).body(null);
		
		UserFullDetails details = service.getAgentDetails(id);
		
		if (details == null)
			return ResponseEntity.status(400).headers(headers).body(null);
		
		return ResponseEntity.ok().headers(headers).body(details);
	}
	
	@PostMapping()
	public ResponseEntity<UserAuthenticationDetails> regstier(
			@RequestHeader("Authorization") String authHeader,
			@RequestBody UserRegisterDetails registerDetails) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Person person = registerDetails.getPerson();

		String jwtoken = authHeader.substring(7);
		String role = jwtService.extractAllClaims(jwtoken).get("role", String.class);
		
		if (role == null || !role.equals("ADMIN"))
			return ResponseEntity.status(401).headers(headers).body(null);
		
		UserAuthenticationDetails details = authService.registerUser(registerDetails);

		if (details == null)
			return ResponseEntity.status(400).headers(headers).body(null);

		return ResponseEntity.ok().headers(headers).body(details);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity updateUser(@PathVariable Integer id,
										 @RequestHeader("Authorization") String authHeader,
										 @RequestBody UserEditDetails profileDetails) {
		
		String jwtoken = authHeader.substring(7);
		String role = jwtService.extractAllClaims(jwtoken).get("role", String.class);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		if (role == null || !role.equals("ADMIN"))
			return ResponseEntity.status(401).headers(headers).body("incorrect role");
		
		boolean isUpdated = service.updateAgentProfile(profileDetails, id);
		
		if (!isUpdated)
			return ResponseEntity.status(400).headers(headers).body("invalid information");
		
		return ResponseEntity.ok().headers(headers).body(profileDetails);
	}

	@PutMapping("/pass/{id}")
	public ResponseEntity<ResetPasswordResponse> resetPassword(@PathVariable Integer id, 
													@RequestHeader("Authorization") String authHeader){
		
		String jwtoken = authHeader.substring(7);
		String role = jwtService.extractAllClaims(jwtoken).get("role", String.class);
		
		if (role == null || !role.equals("ADMIN"))
			return ResponseEntity.status(401).body(null);
		
		ResetPasswordResponse response = service.resetAgentPassword(id);
		
		if (response == null)
			return ResponseEntity.status(400).body(null);
		
		return ResponseEntity.ok(response);
	}

	@PutMapping("/promote/{id}")
	public ResponseEntity<PromoteAgentResponse> promoteAgent(@PathVariable Integer id,
										@RequestHeader("Authorization") String authHeader,
										@RequestBody UserPromoteDetails promoteDetails) {
		
		String jwtoken = authHeader.substring(7);
		String role = jwtService.extractAllClaims(jwtoken).get("role", String.class);
		PromoteAgentResponse response = new PromoteAgentResponse();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		if (role == null || !role.equals("ADMIN"))
			return ResponseEntity.status(401).body(null);
		
		if (promoteDetails == null)
			promoteDetails = new UserPromoteDetails(Roles.ADMIN);
		
		response.setPromoted(service.promoteAgent(promoteDetails, id));
		
		if (!response.isPromoted())
			return ResponseEntity.status(400).body(null);
		
		return ResponseEntity.ok().headers(headers).body(response);
	}
}
