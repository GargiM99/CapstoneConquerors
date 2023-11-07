package ca.ttms.controllers;

import java.util.List;

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

import ca.ttms.beans.details.CreateTripDetails;
import ca.ttms.beans.details.EditTripDetails;
import ca.ttms.beans.details.TripTypeDetails;
import ca.ttms.beans.response.TripResponse;
import ca.ttms.services.AuthenticationService;
import ca.ttms.services.ClientService;
import ca.ttms.services.JWTService;
import ca.ttms.services.TripService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/trip")
@RequiredArgsConstructor
public class TripController {
	
	@Autowired
	private TripService service;
	private final JWTService jwtService;
	private final AuthenticationService authService;
	private final ClientService clientService;
	
	@PostMapping()
	public ResponseEntity<TripResponse> createTrip(
			@RequestHeader("Authorization") String authHeader,
			@RequestBody CreateTripDetails createTripDetails) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String jwtoken = authHeader.substring(7);
		String role = jwtService.extractAllClaims(jwtoken).get("role", String.class);
		String username = jwtService.extractSubject(jwtoken);
		
		if ((role.equals("AGENT") && clientService.verfiyClientToAgent(username, createTripDetails.getClientId())) || role.equals("ADMIN")) {
			TripResponse response = service.createTrip(createTripDetails);
			
			if (response == null)
				return ResponseEntity.status(400).headers(headers).body(null);
			return ResponseEntity.ok().headers(headers).body(response);
		}		
		
		return ResponseEntity.status(401).headers(headers).body(null);
	}
	
	@PutMapping()
	public ResponseEntity<TripResponse> editTrip(
			@RequestHeader("Authorization") String authHeader,
			@RequestBody EditTripDetails editTripDetails) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String jwtoken = authHeader.substring(7);
		String role = jwtService.extractAllClaims(jwtoken).get("role", String.class);
		String username = jwtService.extractSubject(jwtoken);
		int tripId = editTripDetails.getTripDetails().getId();
		
		if ((role.equals("AGENT") && service.verifyTripToAgent(username, tripId)) || role.equals("ADMIN")) {
			
			TripResponse response = service.editTrip(editTripDetails);
			
			if (response == null)
				return ResponseEntity.status(400).headers(headers).body(null);
			return ResponseEntity.ok().headers(headers).body(response);
		}		
		
		return ResponseEntity.status(401).headers(headers).body(null);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TripResponse> getTripDetails(
			@PathVariable Integer id,
			@RequestHeader("Authorization") String authHeader){
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String jwtoken = authHeader.substring(7);
		String role = jwtService.extractAllClaims(jwtoken).get("role", String.class);
		String username = jwtService.extractSubject(jwtoken);
		
		if ((role.equals("AGENT") && service.verifyTripToAgent(username, id)) || role.equals("ADMIN")) {
			TripResponse response = service.getTripDetails(id);
			
			if (response == null)
				return ResponseEntity.status(400).headers(headers).body(null);
			return ResponseEntity.ok().headers(headers).body(response);
		}
		
		return ResponseEntity.status(401).headers(headers).body(null);
	}

	@PostMapping("/type")
	public ResponseEntity<List<TripTypeDetails>> modifyTripType(
			@RequestHeader("Authorization") String authHeader,
			@RequestBody List<TripTypeDetails> tripTypeDetails) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String jwtoken = authHeader.substring(7);
		String role = jwtService.extractAllClaims(jwtoken).get("role", String.class);
		
		if (role.equals("ADMIN")) {
			List<TripTypeDetails> response = service.uploadTripType(tripTypeDetails);
			
			if (response == null)
				return ResponseEntity.status(400).headers(headers).body(null);
			return ResponseEntity.ok().headers(headers).body(response);
		}
		return ResponseEntity.status(401).headers(headers).body(null);
	}
	
	@GetMapping("/type")
	public ResponseEntity<List<TripTypeDetails>> getTripType(@RequestHeader("Authorization") String authHeader) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String jwtoken = authHeader.substring(7);
		String role = jwtService.extractAllClaims(jwtoken).get("role", String.class);
		
		if (role.equals("ADMIN") || role.equals("AGENT")) {
			List<TripTypeDetails> response = service.getTripType();
			
			if (response == null)
				return ResponseEntity.status(400).headers(headers).body(null);
			return ResponseEntity.ok().headers(headers).body(response);
		}
		return ResponseEntity.status(401).headers(headers).body(null);
	}

	@GetMapping("/report/{id}")
    public ResponseEntity<byte[]> generateReport(
    		@PathVariable Integer id,
    		@RequestHeader("Authorization") String authHeader) {
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_PDF);
		
		String jwtoken = authHeader.substring(7);
		String role = jwtService.extractAllClaims(jwtoken).get("role", String.class);
		String username = jwtService.extractSubject(jwtoken);
		
		if ((role.equals("AGENT") && service.verifyTripToAgent(username, id)) || role.equals("ADMIN")) {
			byte[] response = service.generateTripReport(id);
			
			if (response == null)
				return ResponseEntity.status(400).headers(headers).body(null);
			
            headers.setContentDispositionFormData("inline", "event_report.pdf");
			return ResponseEntity.ok().headers(headers).body(response);
		}
		
		return ResponseEntity.status(401).headers(headers).body(null);
    }
}
