import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IChangeEventType, IChangeTripType, IEventType, ITripType } from 'src/app/client/data-access/types/trip/trip-type.interface';
import { EventTypeCardComponent } from '../event-type-card/event-type-card.component';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'trip-type-card',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule, EventTypeCardComponent],
  templateUrl: './trip-type-card.component.html',
  styleUrls: ['./trip-type-card.component.scss']
})
export class TripTypeCardComponent implements OnInit{
  @Input() tripType!: ITripType
  @Input() canEdit: boolean = false
  @Input() tripIndex!: number 

  @Output() removeTrip = new EventEmitter<number>()
  @Output() tripTypeChange = new EventEmitter<IChangeTripType>();

  tripTypeForm: FormGroup;
  templateEventType: IEventType = {eventName:"", eventDescription:"", dateDiff:0};

  onRemove(){
    this.removeTrip.emit(this.tripIndex);
  }

  onRemoveEventType(eventRemoveIndex: number) {
    if (eventRemoveIndex >= 0 && eventRemoveIndex < this.tripType.eventTypes.length){
      const changedTripType: ITripType = {
        typeName: this.tripTypeForm.value.typeName,
        eventTypes: [
          ...this.tripType.eventTypes.slice(0, eventRemoveIndex),
          ...this.tripType.eventTypes.slice(eventRemoveIndex + 1)
        ]
      }

      if (changedTripType !== this.tripType) {
        this.tripType = changedTripType;
        this.tripTypeChange.emit({trip: this.tripType, tripIndex: this.tripIndex});
      }
    }
  }

  onChange(){
    const changedTripType: ITripType = {
      typeName: this.tripTypeForm.value.typeName,
      eventTypes: [...this.tripType.eventTypes]
    };

    if (changedTripType !== this.tripType) {
      this.tripType = changedTripType;
      this.tripTypeChange.emit({trip: this.tripType, tripIndex: this.tripIndex});
    }
  }

  onEventTypeChange(eventTypeChange: IChangeEventType){
    const changedTripType: ITripType = {
      typeName: this.tripTypeForm.value.typeName,
      eventTypes: [
        ...this.tripType.eventTypes.slice(0, eventTypeChange.eventIndex),
        eventTypeChange.event,
        ...this.tripType.eventTypes.slice(eventTypeChange.eventIndex + 1),
      ]
    };

    if (changedTripType !== this.tripType) {
      this.tripType = changedTripType;
      this.tripTypeChange.emit({trip: this.tripType, tripIndex: this.tripIndex});
    }
  }

  onAddEventType(){
    const changedTripType: ITripType = {
      typeName: this.tripTypeForm.value.typeName,
      eventTypes: [
        ...this.tripType.eventTypes,
        this.templateEventType
      ]
    }
    this.tripType = changedTripType;
    this.tripTypeChange.emit({trip: this.tripType, tripIndex: this.tripIndex});
  }

  ngOnInit(): void {
    this.tripTypeForm.patchValue({typeName: this.tripType.typeName})
  }

  constructor(private fb: FormBuilder) {
    this.tripTypeForm = this.fb.group({
      typeName: ['']
    });
  }
}
