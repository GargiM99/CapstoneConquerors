import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientDetailsComponent } from './client-details.component';
import { ReactiveFormsModule } from '@angular/forms';
import { TripListCardComponent } from '../../ui/cards/trip-list-card/trip-list-card.component';

@NgModule({
  declarations: [ClientDetailsComponent],
  imports: [CommonModule, ReactiveFormsModule, TripListCardComponent],
  exports: [ClientDetailsComponent]
})
export class ClientDetailsModule { }
