import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ICalendarEvent } from 'src/app/share/data-access/types/calendar/calender-event.interface';

@Component({
  selector: 'event-tooltip',
  templateUrl: './event-tooltip.component.html',
  styleUrls: ['./event-tooltip.component.scss']
})
export class EventTooltipComponent {
  @Input() event!: ICalendarEvent;
  @Output() closeClick = new EventEmitter<void>();

  close(event: Event): void {
    event.stopPropagation();
    console.log('Close button clicked');
    this.closeClick.emit();
  }
}
