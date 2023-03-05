import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TtmsMealFormComponent } from './ttms-meal-form/ttms-meal-form.component';
import { ReactiveFormsModule } from '@angular/forms';


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
  ]
})
export class TtmsMealsModule { }
