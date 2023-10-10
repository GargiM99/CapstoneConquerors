import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IEventDetails } from 'src/app/client/data-access/types/trip/event-details.interface';

@Component({
  selector: 'event-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './event-card.component.html',
  styleUrls: ['./event-card.component.scss']
})
export class EventCardComponent {
  @Input() event!: IEventDetails
}
