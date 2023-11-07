import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TripDetailsComponent } from './trip-details.component';
import { ReactiveFormsModule } from '@angular/forms';
import { EventListCardComponent } from '../../ui/cards/event-list-card/event-list-card.component';
import { TripService } from '../../data-access/services/trip.service';

@NgModule({
  declarations: [TripDetailsComponent],
  imports: [CommonModule, ReactiveFormsModule, EventListCardComponent],
  providers: [TripService]
})
export class TripDetailsModule { }
