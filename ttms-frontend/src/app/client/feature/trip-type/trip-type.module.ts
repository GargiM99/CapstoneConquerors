import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TripTypeComponent } from './trip-type.component';
import { FormsModule } from '@angular/forms';
import { TripTypeCardComponent } from '../../ui/cards/trip-type-card/trip-type-card.component';

@NgModule({
  declarations: [TripTypeComponent],
  imports: [CommonModule, FormsModule, TripTypeCardComponent],
  exports: [TripTypeComponent]
})
export class TripTypeModule { }
