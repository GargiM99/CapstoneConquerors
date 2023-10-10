import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ITripDetails } from 'src/app/client/data-access/types/trip/trip-details.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'trip-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './trip-card.component.html',
  styleUrls: ['./trip-card.component.scss']
})
export class TripCardComponent {
  @Input() trip!: ITripDetails

  viewDetails(tripId: number){
    this.router.navigate([`trip`, this.trip.id])
  }

  constructor(private router: Router){}
}
