package ca.ttms.services;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ttms.beans.details.MealPriceDetails;

/**
 * Service for meal price including validating a writing
 * 
 * @author Hamza 
 * date: 2023/03/11
 */

@Service
public class MealService {
	
	private JSONService JsonService = new JSONService();
	
	private final double MAX_PRICE = 300.00; 
	private final double MIN_PRICE = 0.00; 
	
	/**
	 * Updates the meal price using MealPriceDetails
	 * 
	 * @param details: The meal details which is going to be updated
	 * @param filePath: The path which the json file is being written to
	 * @return: Determines if object was updated
	 */
	public boolean updateMealPrice(MealPriceDetails details, String filePath) {
		try {
			if (!details.validateMealPrice(MAX_PRICE, MIN_PRICE))
				return false;
			
			JsonService.writeJsonObject(details, filePath);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	
	/**
	 * Updates the meal price using MealPriceDetails for default path
	 * 
	 * @param details: The meal details which is going to be updated
	 * @return: Determines if object was updated
	 */
	public boolean updateMealPrice(MealPriceDetails details) {
		try {
			if (!details.validateMealPrice(MAX_PRICE, MIN_PRICE))
				return false;
			
			JsonService.writeJsonObject(details, this.getDefaultPath());
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	
	/**
	 * Reads from json file and returns the MealPriceDetails
	 * 
	 * @return MealPriceDetails
	 */
	public MealPriceDetails getMealPrice() {
		try {
			MealPriceDetails mealPrice = new MealPriceDetails();
			mealPrice = (MealPriceDetails) JsonService.readJsonFile(this.getDefaultPath(), mealPrice.getClass());
			return mealPrice;
		}
		catch(Exception e){
			return null;
		}
	}
	
	/**
	 * Reads from json file and returns the MealPriceDetails
	 * 
	 * @param: filePath: Path for reading MealPriceDetails
	 * @return: An MealPriceDetails from the json file
	 */
	public MealPriceDetails getMealPrice(String filePath) {
		try {
			MealPriceDetails mealPrice = new MealPriceDetails();
			mealPrice = (MealPriceDetails) JsonService.readJsonFile(filePath, mealPrice.getClass());
			return mealPrice;
		}
		catch(Exception e){
			return null;
		}
	}
	
	/**
	 * Returns the default path for mealPrice file
	 * 
	 * @return: The default path for mealPrice file
	 */
	private String getDefaultPath () {
		String parentDir = new File (System.getProperty("user.dir")).getParentFile().getParent();
		String extraPath = "\\ttms-frontend\\ttms\\src\\assets\\data\\mealPriceData.json";
		
		return parentDir + extraPath;
	}
}
