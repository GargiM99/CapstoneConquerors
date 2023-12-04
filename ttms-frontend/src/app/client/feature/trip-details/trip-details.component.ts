import { Component, OnDestroy, OnInit, ViewContainerRef } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Store, select } from '@ngrx/store';
import { maxDateValidator, minDateValidator } from 'src/app/share/data-access/services/validators/dateValidator';
import { IAppState } from 'src/app/share/data-access/types/app-state.interface';
import { Observable, Subscription, map, take } from 'rxjs';
import * as TripAction from '../../data-access/redux/trip/trip-action'
import { HttpErrorResponse } from '@angular/common/http';
import { tripDetailsSelector, tripErrorSelector, tripIsLoadingSelector, tripTypeSelector } from '../../data-access/redux/trip/trip-selectors';
import { ITripEventDetails } from '../../data-access/types/trip/trip-event-details.interface';
import { IEventDetails } from '../../data-access/types/trip/event-details.interface';
import { TripService } from '../../data-access/services/trip.service';
import { ITripDetails } from '../../data-access/types/trip/trip-details.interface';

@Component({
  selector: 'app-trip-details',
  templateUrl: './trip-details.component.html',
  styleUrls: ['./trip-details.component.scss']
})
export class TripDetailsComponent implements OnInit, OnDestroy{
  MAX_LENGTH = 70;

  tripId: number
  tripDetails$: Observable<ITripEventDetails | null>
  tripError$: Observable<Error | HttpErrorResponse | null>
  tripIsLoading$: Observable<boolean>
  tripSub!: Subscription

  eventDetails$: Observable<IEventDetails[] | undefined>

  tripForm = this.fb.group({
    clientId: [1],
    id: [0],
    tripName: ['', [Validators.required, Validators.maxLength(this.MAX_LENGTH)]],
    tripStartDate: [new Date(), [Validators.required]],
    tripEndDate: [new Date(), [Validators.required]],
    tripType: [''],
    status: ['', [Validators.required]]        
  })

  isInvalid(groupName: string, fieldName: string): boolean {
    const control = this.tripForm.get(groupName)?.get(fieldName)
    return control ? control.invalid && (control.dirty || control.touched) : true
  } 

  ngOnInit(): void {
    this.store.dispatch(TripAction.getTripDetails({ tripId: this.tripId }))

    this.tripSub = this.tripDetails$.subscribe((data) => {
      if (data) {
        this.tripForm.reset()
        this.tripForm.patchValue({
          id: this.tripId,
          clientId: data.tripDetails.clientId,
          tripName: data.tripDetails.tripName.toString(),
          tripStartDate: data.tripDetails.tripStartDate,
          tripEndDate: data.tripDetails.tripEndDate,
          status: data.tripDetails.status.toString()
        })
      }
    })
  }

  printSchedule(){this.tripService.printTripSchedule(this.tripId)}

  updateTrip(){
    let updatedTrip = <ITripEventDetails>this.tripForm.value
    this.eventDetails$
    .pipe(take(1))
    .subscribe((eventDetails) => {
      let updatedTrip: ITripEventDetails = {
        tripDetails: <ITripDetails>this.tripForm.value,
        eventDetails: eventDetails ?? []
      }
      this.store.dispatch(TripAction.updateTrip({ tripDetails: updatedTrip }));
    });
  }

  ngOnDestroy(): void { this.tripSub.unsubscribe() }

  constructor(private store: Store<IAppState>, private route: ActivatedRoute,
    private fb: FormBuilder, private viewContainerRef: ViewContainerRef, 
    private tripService: TripService){

      this.tripId = +(this.route.snapshot.paramMap.get('id') ?? 0)

      this.tripIsLoading$ = this.store.pipe(select(tripIsLoadingSelector))
      this.tripError$ = this.store.pipe(select(tripErrorSelector))
      this.tripDetails$ = this.store.pipe(select(tripDetailsSelector))

      this.eventDetails$ = this.tripDetails$.pipe(map((trip) => trip?.eventDetails));
  }
}
