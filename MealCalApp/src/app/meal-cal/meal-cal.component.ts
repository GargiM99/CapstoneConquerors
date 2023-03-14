import { Component } from '@angular/core';
import{MealService} from'./meal.service';
@Component({
  selector: 'app-meal-cal',
  templateUrl: './meal-cal.component.html',
  styleUrls: ['./meal-cal.component.css']
})
export class MealCalComponent {
  adults: number = 0;
  children: number = 0;
  days: number = 0;
  FulldaysPerService: number = 0;
  QuickdaysPerService: number = 0;
  snacksPreference: boolean = true;
  mealPrice: number = 0;
  snackPrice: number=0;
  totalPrice: number=0;
  totalPricePerDay: number=0;
  adultsError: string = '';
  childrenError: string = '';
  daysError: string = '';
  FulldaysPerServiceError: string = '';
  QuickdaysPerServiceError: string = '';

  constructor(private mealService: MealService) {}

  onSubmit() {
    // Validate user input
    this.mealPrice = this.mealService.calculateMealPrice(this.adults, this.children,this.days,  this.FulldaysPerService,this. QuickdaysPerService, this.snacksPreference);
      this.snackPrice = this.snacksPreference ? 5 : 0;
      this.totalPricePerDay = this.mealPrice + this.snackPrice;
      this.totalPrice = this.totalPricePerDay * this.days * (this.FulldaysPerService+this.QuickdaysPerService);
    this.adultsError = this.validateNumberInput(this.adults, 'Number of adults');
    this.childrenError = this.validateNumberInput(this.children, 'Number of children');
    this.daysError = this.validateNumberInput(this.days, 'Number of days');
    this.FulldaysPerServiceError = this.validateNumberInput(this.FulldaysPerService, 'Number of days for Full Service');
    this.QuickdaysPerServiceError = this.validateNumberInput(this.QuickdaysPerService, 'Number of days for Quick Service');

    if (!this.adultsError && !this.childrenError && !this.daysError && !this.FulldaysPerServiceError && !this.QuickdaysPerServiceError) {
      // Calculate meal price using the meal service
      this.mealPrice = this.mealService.calculateMealPrice(this.adults, this.children, this.days, this.FulldaysPerService, this.QuickdaysPerService, this.snacksPreference);
    }
  }

  validateNumberInput(inputValue: number, inputName: string): string {
    if (inputValue <= 0 || isNaN(inputValue)) {
      return `${inputName}Enter a positive number above 0.`;
    }
    return '';
  }
}


