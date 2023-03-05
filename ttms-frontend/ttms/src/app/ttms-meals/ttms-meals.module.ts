import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TtmsMealFormComponent } from './ttms-meal-form/ttms-meal-form.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MealPriceService } from '../services/meal-price.service';


@NgModule({
  declarations: [
    TtmsMealFormComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  exports: [
    TtmsMealFormComponent
  ],
  providers: [
    MealPriceService
  ]
})
export class TtmsMealsModule { }
