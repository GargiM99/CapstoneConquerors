package ca.ttms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.ttms.beans.details.MealPriceDetails;
import ca.ttms.services.MealService;

/**
 * REST controller for meal prices for the calculator
 * 
 * @author Hamza
 * date: 2023/03/07 
 */

@RestController
@RequestMapping("/api/meals")
public class MealController {

	@Autowired
	private MealService mealService;
	
	/**
	 * Sends a list of all the meal prices for the calculator
	 * @return MealPriceDetails
	 */
	@GetMapping()
	public ResponseEntity<MealPriceDetails> getMeals() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		MealPriceDetails response = mealService.getMealPrice();
		
		if (response == null)
			return ResponseEntity.status(401).headers(headers).body(response);
			
		return ResponseEntity.ok().headers(headers).body(response);
	}
	
	/**
	 * Updates meal price from the frontend
	 * 
	 * @param mealDetails
	 * @return ResponseMealUpdate
	 */
	@PutMapping()
	public ResponseEntity<MealPriceDetails> changeMeals(@RequestBody MealPriceDetails mealDetails) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		boolean isUpdated = mealService.updateMealPrice(mealDetails);
		
		if (isUpdated)
			return ResponseEntity.ok().headers(headers).body(mealDetails);
		
		return ResponseEntity.status(401).headers(headers).body(null);
	}
}
