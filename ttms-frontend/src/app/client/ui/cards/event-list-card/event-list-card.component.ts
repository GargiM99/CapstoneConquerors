import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Observable, of } from 'rxjs';
import { IEventDetails } from '../../../data-access/types/trip/event-details.interface';
import { EventCardComponent } from 'src/app/share/ui/cards/event-card/event-card.component';

@Component({
  selector: 'event-list-card',
  standalone: true,
  imports: [CommonModule, EventCardComponent],
  templateUrl: './event-list-card.component.html',
  styleUrls: ['./event-list-card.component.scss']
})
export class EventListCardComponent {
  @Input() events$: Observable<IEventDetails[] | undefined> = of([])
}
