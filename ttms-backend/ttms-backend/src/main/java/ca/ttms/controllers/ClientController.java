package ca.ttms.controllers;

import java.util.List;
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

import ca.ttms.beans.details.UserAuthenticationDetails;
import ca.ttms.beans.details.UserEditDetails;
import ca.ttms.beans.details.UserRegisterDetails;
import ca.ttms.beans.response.BasicClientResponse;
import ca.ttms.beans.response.ClientDetailsResponse;
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

	@GetMapping()
	public ResponseEntity<List<Map<String,Object>>> getClients(@RequestHeader("Authorization") String authHeader){
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String jwtoken = authHeader.substring(7);
		String role = jwtService.extractAllClaims(jwtoken).get("role", String.class);
		String username = jwtService.extractSubject(jwtoken);
		
		if (role.equals("AGENT")) {
			List<Map<String,Object>> response = service.getClients(username);
			
			if (response == null)
				return ResponseEntity.status(400).headers(headers).body(null);
			return ResponseEntity.ok().headers(headers).body(response);
		}
		
		if (role.equals("ADMIN")) {
			List<Map<String,Object>> response = service.getClients();
			
			if (response == null)
				return ResponseEntity.status(400).headers(headers).body(null);
			return ResponseEntity.ok().headers(headers).body(response);
		}
		return ResponseEntity.status(401).headers(headers).body(null);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClientDetailsResponse> getClientDetails(
			@RequestHeader("Authorization") String authHeader,
			@PathVariable Integer id){
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String jwtoken = authHeader.substring(7);
		String role = jwtService.extractAllClaims(jwtoken).get("role", String.class);
		String username = jwtService.extractSubject(jwtoken);
		
		if ((role.equals("AGENT") && service.verfiyClientToAgent(username, id)) || role.equals("ADMIN")) {
			ClientDetailsResponse response = service.getClientDetails(id);
			
			if (response == null)
				return ResponseEntity.status(400).headers(headers).body(null);
			return ResponseEntity.ok().headers(headers).body(response);
		}
		return ResponseEntity.status(401).headers(headers).body(null);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserEditDetails> editClient(
			@RequestHeader("Authorization") String authHeader,
			@PathVariable Integer id,
			@RequestBody UserEditDetails clientDetails) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String jwtoken = authHeader.substring(7);
		String role = jwtService.extractAllClaims(jwtoken).get("role", String.class);
		String username = jwtService.extractSubject(jwtoken);
		
		if ((role.equals("AGENT") && service.verfiyClientToAgent(username, id)) || role.equals("ADMIN")) {
			UserEditDetails response = service.editClientDetails(clientDetails, id);
			
			if (response == null)
				return ResponseEntity.status(400).headers(headers).body(null);
			return ResponseEntity.ok().headers(headers).body(response);
		}
		return ResponseEntity.status(401).headers(headers).body(null);
		
	}
}
