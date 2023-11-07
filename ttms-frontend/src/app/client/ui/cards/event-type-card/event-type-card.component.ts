import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IChangeEventType, IEventType } from 'src/app/client/data-access/types/trip/trip-type.interface';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'event-type-card',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './event-type-card.component.html',
  styleUrls: ['./event-type-card.component.scss']
})
export class EventTypeCardComponent implements OnInit{
  @Input() eventType!: IEventType
  @Input() canEdit: boolean = false
  @Input() eventIndex!: number

  @Output() removeEvent = new EventEmitter<number>()
  @Output() eventTypeChange = new EventEmitter<IChangeEventType>();

  eventTypeForm: FormGroup;

  onRemove(){
    this.removeEvent.emit(this.eventIndex)
  }

  onChange(){
    const changeEventType = <IEventType>this.eventTypeForm.value;

    if (changeEventType !== this.eventType) {
      this.eventType = changeEventType;
      this.eventTypeChange.emit({event: this.eventType, eventIndex: this.eventIndex});
    }
  }

  constructor(private fb: FormBuilder){
    this.eventTypeForm = this.fb.group({
      eventName: [''],
      eventDescription: [''],
      dateDiff: [0]
    });
  }

  ngOnInit(): void {
    this.eventTypeForm.patchValue(<IEventType>{
      eventName: this.eventType.eventName,
      eventDescription: this.eventType.eventDescription,
      dateDiff: this.eventType.dateDiff
    })
  }
}
