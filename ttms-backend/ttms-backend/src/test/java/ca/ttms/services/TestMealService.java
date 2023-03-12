package ca.ttms.services;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import ca.ttms.beans.MealPriceDetails;

/**
 * description: Test meal service for reading and writing
 * @author hamza
 * @since: 2022/03/12
 */
class TestMealService {
	
	private final MealService service = new MealService();
	
	//Test updating price with proper input
	@Test
	void testPassUpdateMealPrice1() {
		MealPriceDetails details = new MealPriceDetails(12.99,12.99,12.99,12.99,12.99);
		assertTrue(service.updateMealPrice(details), "File should be updated");
	}
	
	//Test if there is a incorrect input as with only one price
	@Test
	void testPassUpdateMealPrice2() {
		MealPriceDetails details = new MealPriceDetails();
		details.setFaPrice(12.99);
		assertFalse(service.updateMealPrice(details), "Mealprice should be invalid");
	}
	
	//Test update meal price with null as a filepath
	@Test
	void testFailUpdateMealPrice() {
		MealPriceDetails details = new MealPriceDetails(12.99,12.99,12.99,12.99,12.99);
		assertFalse(service.updateMealPrice(details,null), "File should return false");
	}
	
	//Test getting meal prices for a specific location
	@Test
	void testPassGetMealPrice1() {
		MealPriceDetails details = new MealPriceDetails(12.99,12.99,12.99,12.99,12.99);
		service.updateMealPrice(details);
		MealPriceDetails checkDetails = service.getMealPrice();
		assertTrue(details.equals(checkDetails), "File should be the same");
	}
	
	//Test getting meal prices for a specific location
	@Test
	void testPassGetMealPrice2() {
		String parentDir = new File (System.getProperty("user.dir")).getParentFile().getParent();
		String extraPath = "\\ttms-frontend\\ttms\\src\\assets\\data\\mealPrice.json";
		
		MealPriceDetails details = new MealPriceDetails(18.99, 13.99, 5.99, 11.99, 4.99);
		service.updateMealPrice(details, parentDir + extraPath);
		MealPriceDetails checkDetails = service.getMealPrice(parentDir + extraPath);
		assertTrue(details.equals(checkDetails), "File should be the same");
	}
	
	//Test if read location is different then write
	@Test
	void testFailGetMealPrice() {
		String parentDir = new File (System.getProperty("user.dir")).getParentFile().getParent();
		String extraPath = "\\ttms-frontend\\ttms\\src\\assets\\data\\mealPrice.json";
		
		MealPriceDetails details = new MealPriceDetails();
		service.updateMealPrice(details, parentDir + extraPath);
		
		String wrongExtraPath = "\\ttms-frontend\\ttms\\src\\assets\\data\\mealPri.json";
		MealPriceDetails checkDetails = service.getMealPrice(parentDir + wrongExtraPath);
		assertTrue(checkDetails == null, "File should be differnet and return null");
	}
}
