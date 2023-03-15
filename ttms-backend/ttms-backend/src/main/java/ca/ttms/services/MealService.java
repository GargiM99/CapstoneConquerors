package ca.ttms.services;

import java.io.File;

import org.springframework.stereotype.Service;

import ca.ttms.beans.MealPriceDetails;

/**
 * @author Hamza 
 * date: 2023/03/11 
 * description: Service for meal price including validating a writing
 */

@Service
public class MealService {
	
	private final JSONService JsonService = new JSONService();
	private final double MAX_PRICE = 300.00; 
	private final double MIN_PRICE = 0.00; 
	
	/**
	 * Description: Updates the meal price using MealPriceDetails
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
	 * Description: Updates the meal price using MealPriceDetails for default path
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
	 * Description: Reads from json file and returns the MealPriceDetails
	 * @return
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
	 * Description: Reads from json file and returns the MealPriceDetails
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
	 * Description: Returns the default path for mealPrice file
	 * @return: The default path for mealPrice file
	 */
	private String getDefaultPath () {
		String parentDir = new File (System.getProperty("user.dir")).getParentFile().getParent();
		String extraPath = "\\ttms-frontend\\ttms\\src\\assets\\data\\mealPriceData.json";
		
		return parentDir + extraPath;
	}
}
