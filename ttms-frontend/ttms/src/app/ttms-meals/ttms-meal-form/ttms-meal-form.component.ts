import { Component } from '@angular/core';
import { MealPrices } from 'src/app/classes/meal-prices';
import { FormGroup, FormControl, Validators } from '@angular/forms';

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
  
  //Valdiator for the meal prices
  mealPrice = new FormGroup({
    qaPrice: new FormControl(0, [
      Validators.required,
      Validators.min(0),
      Validators.max(250)
    ]),
    qcPrice: new FormControl(0, [
      Validators.required,
      Validators.min(0),
      Validators.max(250)
    ]),
    faPrice: new FormControl(0, [
      Validators.required,
      Validators.min(0),
      Validators.max(450)
    ]),
    fcPrice: new FormControl(0, [
      Validators.required,
      Validators.min(0),
      Validators.max(450)
    ]),
    snackPrice: new FormControl(0, [
      Validators.required,
      Validators.min(0),
      Validators.max(250)
    ])
  })

  submitMealForm(){
    alert('test')
  }
  constructor(){}
}
