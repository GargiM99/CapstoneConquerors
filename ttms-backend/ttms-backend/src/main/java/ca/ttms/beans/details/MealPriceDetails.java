package ca.ttms.beans.details;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for validating meal Price
 * 
 * @author Hamza  
 * date: 2023/03/08 
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealPriceDetails {
    private double qaPrice;
    private double qcPrice;
    private double faPrice;
    private double fcPrice; 
    private double snackPrice; 
    
	public boolean validateMealPrice (double max_price, double min_price) {
		if (this.getFaPrice() <= min_price || this.getFaPrice() >= max_price)
			return false;
		
		else if (this.getFcPrice() <= min_price || this.getFcPrice() >= max_price)
			return false;
		
		else if (this.getQaPrice() <= min_price || this.getQaPrice() >= max_price)
			return false;
		
		else if (this.getQcPrice() <= min_price || this.getQcPrice() >= max_price)
			return false;
		
		else if (this.getSnackPrice() <= min_price || this.getSnackPrice() >= max_price)
			return false;
		
		return true;
	}
}
