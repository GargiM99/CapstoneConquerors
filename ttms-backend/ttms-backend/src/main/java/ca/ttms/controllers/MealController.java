package ca.ttms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.ttms.beans.details.MealPriceDetails;
import ca.ttms.beans.response.ResponseMealUpdate;
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
		return ResponseEntity.ok().headers(headers).body(mealService.getMealPrice());
	}
	
	/**
	 * Updates meal price from the frontend
	 * 
	 * @param mealDetails
	 * @return ResponseMealUpdate
	 */
	@PostMapping()
	public ResponseEntity<ResponseMealUpdate> changeMeals(@RequestBody MealPriceDetails mealDetails) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseMealUpdate response = new ResponseMealUpdate(mealService.updateMealPrice(mealDetails));
		return ResponseEntity.ok().headers(headers).body(response);
	}
}
