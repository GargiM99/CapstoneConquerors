import { Component } from '@angular/core';
import { MealPrices } from 'src/app/classes/meal-prices';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MealPriceService } from 'src/app/services/meal-price.service';
import { Router } from '@angular/router';

/*
* author: Hamza Atcha
* date: 2023-03-05
* description: Meal price form for calculator with validation
*/

@Component({
  selector: 'app-ttms-meal-form',
  templateUrl: './ttms-meal-form.component.html',
  styleUrls: ['./ttms-meal-form.component.scss']
})

export class TtmsMealFormComponent {

  importMealPrices : MealPrices = this.mealPriceService.mealPrices;

  constructor(private mealPriceService : MealPriceService, private router: Router){}

  //Valdiator for the meal prices
  mealPrice = new FormGroup({
    qaPrice: new FormControl(this.importMealPrices.qaPrice, [
      Validators.required,
      Validators.min(MealPriceService.MIN_PRICE),
      Validators.max(MealPriceService.MAX_PRICE)
    ]),
    qcPrice: new FormControl(this.importMealPrices.qcPrice, [
      Validators.required,
      Validators.min(MealPriceService.MIN_PRICE),
      Validators.max(MealPriceService.MAX_PRICE)
    ]),
    faPrice: new FormControl(this.importMealPrices.faPrice, [
      Validators.required,
      Validators.min(MealPriceService.MIN_PRICE),
      Validators.max(MealPriceService.MAX_PRICE + 100)
    ]),
    fcPrice: new FormControl(this.importMealPrices.fcPrice, [
      Validators.required,
      Validators.min(MealPriceService.MIN_PRICE),
      Validators.max(MealPriceService.MAX_PRICE + 100)
    ]),
    snackPrice: new FormControl(this.importMealPrices.snackPrice, [
      Validators.required,
      Validators.min(MealPriceService.MIN_PRICE),
      Validators.max(MealPriceService.MAX_PRICE - 50) 
    ])
  })

  //Updates the meal price on the server
  submitMealForm(){
    let newMealPrices = <MealPrices>this.mealPrice.value
    let validPrices = this.mealPriceService.saveMealPrices(newMealPrices) 

    if (validPrices){
      this.router.navigate(['/']);
    }
    
    else{
      alert("Could not update price")
    }
  }
}
