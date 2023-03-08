package ca.ttms.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hamza & Akshat 
 * date: 2023/03/07 
 * description: REST controller for meal prices for the calculator
 */

@RestController
@RequestMapping("/api/meals")
public class MealController {

	// TODO: Create a meal price object to return
	// Sends a list of all the meal prices for the calculator
	public ResponseEntity<String> getMeals() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return ResponseEntity.ok().headers(headers).body("test");
	}
}
