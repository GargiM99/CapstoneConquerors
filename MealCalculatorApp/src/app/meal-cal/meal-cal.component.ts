import { Component } from '@angular/core';
import { MealService } from'./meal-cal.service';
@Component({
  selector: 'app-meal-cal',
  templateUrl: './meal-cal.component.html',
  styleUrls: ['./meal-cal.component.css']
})
export class MealCalComponent {
  adults: number=0;
  children: number=0;
  days: number=0;
  FulldaysPerService: number=0;
  QuickdaysPerService: number=0;
  snacksPreference: boolean=false;
  mealPrice: number=0;
  snackPrice: number=0;
  totalPrice: number=0;
  totalPricePerDay: number=0;
  errorMessage: string='';
  adultsError: string = '';


  constructor(private mealService: MealService) {}
  onSubmit() {
    try {
      // Calculate the prices using the service
      this.mealPrice = this.mealService.calculateMealPrice(this.adults, this.children,this.days,  this.FulldaysPerService,this. QuickdaysPerService, this.snacksPreference);
      this.snackPrice = this.snacksPreference ? 5 : 0;
      this.totalPricePerDay = this.mealPrice + this.snackPrice;
      this.totalPrice = this.totalPricePerDay * this.days * (this.FulldaysPerService+this.QuickdaysPerService);
       // Clear error message if calculation succeeds
       this.errorMessage = '';
    } catch (e) {
      console.error(e);
      this.errorMessage = "An error occurred while calculating the meal prices. Please try again later.";
  }
    }
  }


