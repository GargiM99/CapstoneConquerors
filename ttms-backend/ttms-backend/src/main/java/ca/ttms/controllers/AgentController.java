package ca.ttms.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.ttms.beans.details.BasicUsersDetails;
import ca.ttms.beans.details.MealPriceDetails;
import ca.ttms.services.AgentService;
import ca.ttms.services.AuthenticationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/agent")
@RequiredArgsConstructor
public class AgentController {
	
	@Autowired
	private AgentService service;
	
	@GetMapping()
	public Map<String, Object>[] getMeals() {
		return service.getAgents();
	}
}
