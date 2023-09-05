import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TtmsDashboardContentComponent } from './ttms-dashboard-content/ttms-dashboard-content.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    TtmsDashboardContentComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class TtmsDashboardModule { }
