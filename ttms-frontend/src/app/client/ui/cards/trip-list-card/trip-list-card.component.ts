import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TripCardComponent } from '../../../../share/ui/cards/trip-card/trip-card.component';
import { ITripDetails } from 'src/app/client/data-access/types/trip/trip-details.interface';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'trip-list-card',
  standalone: true,
  imports: [CommonModule, TripCardComponent],
  templateUrl: './trip-list-card.component.html',
  styleUrls: ['./trip-list-card.component.scss']
})
export class TripListCardComponent {
  @Input() trips$: Observable<ITripDetails[] | undefined> = of([])
  @Output() createTrip = new EventEmitter<void>()

  onCreateTrip(){ this.createTrip.emit() }

  constructor(){}
}
