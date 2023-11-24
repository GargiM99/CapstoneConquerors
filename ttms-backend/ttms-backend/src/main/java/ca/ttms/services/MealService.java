package ca.ttms.services;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;

import ca.ttms.beans.details.MealPriceDetails;
import lombok.RequiredArgsConstructor;

/**
 * Service for meal price including validating a writing
 * 
 * @author Hamza 
 * date: 2023/03/11
 */

@Service
@RequiredArgsConstructor
public class MealService {
	
	private final JSONService JsonService;
	private final BlobService blobService;
	
	private final double MAX_PRICE = 300.00; 
	private final double MIN_PRICE = 0.00; 
	
	@Value("${spring.cloud.azure.storage.blob.meal-blob-name}")
	private String mealBlobName;
	
	@Value("${spring.cloud.azure.storage.blob.container-name}")
	private String containerName;

	
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
			
			blobService.uploadJsonBlob(containerName, mealBlobName, details);
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
			TypeToken<MealPriceDetails> typeToken = new TypeToken<MealPriceDetails>() {};
			MealPriceDetails mealPrice = (MealPriceDetails) blobService.downloadJsonBlob(containerName, mealBlobName, typeToken);
			return mealPrice;
		}
		catch(Exception e){
			return null;
		}
	}
}
