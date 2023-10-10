import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TripDetailsComponent } from './trip-details.component';
import { ReactiveFormsModule } from '@angular/forms';
import { EventListCardComponent } from '../../ui/event-list-card/event-list-card.component';

@NgModule({
  declarations: [TripDetailsComponent],
  imports: [CommonModule, ReactiveFormsModule, EventListCardComponent]
})
export class TripDetailsModule { }
