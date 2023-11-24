import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MealPriceComponent } from './meal-price.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [MealPriceComponent],
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  exports: [MealPriceComponent]
})
export class MealPriceModule { }
