import { Component, ElementRef, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IModal } from 'src/app/share/data-access/types/modal/modal.interface';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { maxDateValidator, minDateValidator } from 'src/app/share/data-access/services/validators/dateValidator';
import { Observable } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { ITripCreateDetails, ITripCreateModalDetails } from '../../../data-access/types/trip/create-trip-details.interface';
import * as TripAction from '../../../data-access/redux/trip/trip-action'
import { IAppState } from 'src/app/share/data-access/types/app-state.interface';
import { Store, select } from '@ngrx/store';
import { tripDetailsSelector, tripErrorSelector } from '../../../data-access/redux/trip/trip-selectors';
import { ITripEventDetails } from '../../../data-access/types/trip/trip-event-details.interface';

@Component({
  selector: 'app-trip-create-modal',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './trip-create-modal.component.html',
  styleUrls: ['./trip-create-modal.component.scss']
})
export class TripCreateModalComponent implements IModal, OnInit{
  @Output() closeEvent = new EventEmitter()
  @Input() inputValue!: ITripCreateModalDetails

  MAX_LENGTH = 70;
  tripError$: Observable<HttpErrorResponse | Error | null>
  tripDetails$: Observable<ITripEventDetails | null>
  isSubmitted: boolean = false

  tripForm = this.fb.group({
    clientId: [0],
    tripName: ['', [Validators.required, Validators.maxLength(this.MAX_LENGTH)]],
    tripEndDate: [new Date(), [Validators.required, minDateValidator]],  
    tripType: ['']      
  })

  isInvalid(fieldName: string): boolean {
    const control = this.tripForm?.get(fieldName)
    return control ? control.invalid && (control.dirty || control.touched) : true
  }

  onCreate(){
    let newTrip = <ITripCreateDetails>this.tripForm.value
    newTrip.clientId = this.inputValue.clientId 
    this.store.dispatch(TripAction.createTrip ({ createDetails: newTrip }))
    this.isSubmitted = true
  }
  
  close() {
    this.elementRef.nativeElement.remove()
  }

  submit() {
    this.elementRef.nativeElement.remove()
  }

  ngOnInit(): void {
    this.tripForm.patchValue({clientId: this.inputValue.clientId})
  }

  constructor(private store: Store<IAppState>, private elementRef: ElementRef, 
              private fb: FormBuilder){
          
    this.tripError$ = this.store.pipe(select(tripErrorSelector))
    this.tripDetails$ = this.store.pipe(select(tripDetailsSelector))
  }
} 
