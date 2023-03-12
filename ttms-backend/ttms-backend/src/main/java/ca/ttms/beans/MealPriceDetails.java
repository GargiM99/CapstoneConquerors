package ca.ttms.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
