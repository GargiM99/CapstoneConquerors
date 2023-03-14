import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MealService {

  calculateMealPrice(adults: number, children: number, days: number, _FulldaysPerService: number,_QuickdaysPerService:number, snacksPreference: boolean): number {
    // Validate user input
    if (adults <= 0 || children <= 0 || days <= 0 || _FulldaysPerService <= 0 || _QuickdaysPerService <= 0) {
      const errorMessage = 'Invalid input';
      window.alert(errorMessage);
      return 0; // Return null to indicate an error occurred
      //throw new Error('Invalid input');
    }

    // Calculate the average meal price per person per day
    const mealsPerDay = adults + children;
    const mealPricePerDay = 10; // Sample value
    const mealPricePerPersonPerDay = mealPricePerDay / mealsPerDay;

    // Calculate the price of snacks
    const snackPricePerDay = snacksPreference ? 5 : 0;

    // Calculate the total price
    const totalPricePerDay = mealPricePerPersonPerDay + snackPricePerDay;
    const totalPrice = totalPricePerDay * days * (_FulldaysPerService+_QuickdaysPerService);

    return totalPrice;
  }
}
