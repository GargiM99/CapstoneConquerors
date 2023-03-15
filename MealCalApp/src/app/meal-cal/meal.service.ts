import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MealService {
  adultsError: string='';
  childrenError: string='';
  daysError: string='';
  FulldaysPerServiceError: string='';
  QuickdaysPerServiceError: string='';

  calculateMealPrice(adults: number, children: number, days: number, _FulldaysPerService: number,_QuickdaysPerService:number, snacksPreference: boolean): number {
    // Validate user input
    if (adults <= 0) {
      this.adultsError = 'Invalid input';
    } else {
      this.adultsError = '';
    }
    if (children <= 0) {
      this.childrenError = 'Invalid input';
    } else {
      this.childrenError = '';
    }
    if (days <= 0) {
      this.daysError = 'Invalid input';
    } else {
      this.daysError = '';
    }
    if (_FulldaysPerService <= 0) {
      this.FulldaysPerServiceError = 'Invalid input';
    } else {
      this.FulldaysPerServiceError = '';
    }
    if (_QuickdaysPerService <= 0) {
      this.QuickdaysPerServiceError = 'Invalid input';
    } else {
      this.QuickdaysPerServiceError = '';
    }

    if (this.adultsError || this.childrenError || this.daysError || this.FulldaysPerServiceError || this.QuickdaysPerServiceError) {
      return 0;
    }

    // Perform calculation
    const fullPrice = 20;
    const quickPrice = 15;



  /*  const fullDays = Math.ceil(days / _FulldaysPerService);
    const quickDays = Math.ceil(days / _QuickdaysPerService);
    totalPrice += fullDays * fullPrice * (adults + children);
    totalPrice += quickDays * quickPrice * (adults + children);

    return totalPrice;*/
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
