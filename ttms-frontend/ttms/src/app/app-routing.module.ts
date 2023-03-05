import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TtmsMealFormComponent } from './ttms-meals/ttms-meal-form/ttms-meal-form.component';

const routes: Routes = [
  { path: 'meals', component: TtmsMealFormComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
