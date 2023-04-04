package ca.ttms.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ca.ttms.beans.details.MealPriceDetails;

/**
 * Test meal service for reading and writing
 * 
 * @author hamza
 * @Date: 2022/03/12
 */
class TestMealService {
	
//	private final MealService service = new MealService();
//	private final MealPriceDetails intialMealPrice = service.getMealPrice();
	
	private MealService mealService;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	//Test updating price with proper input
	void Update_MealPrice_CheckMealPriceIsValid() {
		//Arrange
		MealPriceDetails inputDetails = new MealPriceDetails(12.99,12.99,12.99,12.99,12.99);
		
		//Act & Assert
		assertTrue(mealService.updateMealPrice(inputDetails), "File should be updated");
	}
	
	@Test
	//Test invalid meal price with incorrect values
	void Update_MealPrice_CheckMealPriceIsInvalid() {
		//Arrange
		MealPriceDetails inputDetails = new MealPriceDetails();
		inputDetails.setFaPrice(12.99);
		
		//Act & Assert
		assertFalse(mealService.updateMealPrice(inputDetails), "Mealprice should be invalid");
	}

	@Test
	//Test update meal price with null as a filepath
	void Update_MealPriceWithInvalidFilePath_CheckInvalidFilePath() {
		//Arrange
		MealPriceDetails inputDetails = new MealPriceDetails(12.99,12.99,12.99,12.99,12.99);
		String inputFilePath = null;
		
		//Act & Assert
		assertFalse(mealService.updateMealPrice(inputDetails,inputFilePath), "File should return false");
	}

	@Test
	//Test getting meal prices for a specific location
	void Get_MealPrice_CompareUpdatePriceWithResult() {
		//Arrange
		MealPriceDetails resultDetails;
		MealPriceDetails inputDetails = new MealPriceDetails(12.99,12.99,12.99,12.99,12.99);
		MealPriceDetails expectedDetails = inputDetails;
		
		//Act
		mealService.updateMealPrice(inputDetails);
		resultDetails = mealService.getMealPrice();
		
		//Assert
		assertTrue(resultDetails.equals(expectedDetails), "File should be the same");
	}

	@Test
	//Test getting meal prices for a specific location
	void Get_MealPriceWithPath_CompareUpdatePriceWithResult() {
		//Arrange
		String parentDir = new File (System.getProperty("user.dir")).getParentFile().getParent();
		String extraPath = "\\ttms-frontend\\ttms\\src\\assets\\data\\mealPrice.json";
		String inputFilePath = parentDir + extraPath;
		
		MealPriceDetails inputDetails = new MealPriceDetails(18.99, 13.99, 5.99, 11.99, 4.99);
		MealPriceDetails expectedDetails = inputDetails;
		
		//Act
		boolean test = mealService.updateMealPrice(inputDetails, inputFilePath);
		MealPriceDetails resultDetails = mealService.getMealPrice(inputFilePath);
		
		//Assert
		assertTrue(inputDetails.equals(resultDetails), "" + inputFilePath);
	}

	@Test
	//Test if read location is different then write
	void Get_MealPriceWithInvalidPath_CheckResultIsNull() {
		//Arrange
		MealPriceDetails expectedDetails = null;
		
		String parentDir = new File (System.getProperty("user.dir")).getParentFile().getParent();
		String extraPath = "\\ttms-frontend\\ttms\\src\\assets\\data\\mealPrice.json";
		String inputFilePath = parentDir + extraPath;
		
		String wrongExtraPath = "\\ttms-frontend\\ttms\\src\\assets\\data\\mealPri.json";
		String wrongFilePath = parentDir + wrongExtraPath;
		
		//Act
		MealPriceDetails inputDetails = new MealPriceDetails(10.99, 8.99, 20.99, 14.99, 4.99);
		mealService.updateMealPrice(inputDetails, inputFilePath);
		MealPriceDetails resultDetails = mealService.getMealPrice(wrongFilePath);
		
		//Assert
		assertEquals(resultDetails, expectedDetails, "File should be differnet and return null");
	}
	
	@After(value = "") 
	void RevertFile() {
		//service.updateMealPrice(intialMealPrice);
	}
}
