import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TtmsMealFormComponent } from './ttms-meal-form/ttms-meal-form.component';



@NgModule({
  declarations: [
    TtmsMealFormComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    TtmsMealFormComponent
  ]
})
export class TtmsMealsModule { }
