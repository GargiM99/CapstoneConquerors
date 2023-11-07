import { Component, OnDestroy, OnInit } from '@angular/core';
import { Store, select } from '@ngrx/store';
import { IAppState } from 'src/app/share/data-access/types/app-state.interface';
import { tripErrorSelector, tripTypeSelector } from '../../data-access/redux/trip/trip-selectors';
import { IChangeTripType, IRemoveEventType, ITripType } from '../../data-access/types/trip/trip-type.interface';
import { Observable, Subscription, catchError, concatMap, map, of, switchMap, take } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import * as TripAction from '../../data-access/redux/trip/trip-action'

@Component({
  selector: 'app-trip-type',
  templateUrl: './trip-type.component.html',
  styleUrls: ['./trip-type.component.scss']
})
export class TripTypeComponent implements OnInit, OnDestroy{
  tripType$ : Observable<ITripType[]>
  tripError$: Observable<HttpErrorResponse | Error | null>
  canEdit: boolean = true
  tripTypeSubscription!: Subscription;
  isFetching = false;

  onAdd() {
    this.tripType$ = this.tripType$.pipe(
      concatMap((tripType) => {
        const newTripType: ITripType = { typeName: "t", eventTypes: [{ eventName: "t", eventDescription: "t", dateDiff: 0 }] };
        const updatedTripType = [...tripType, newTripType]
        return of(updatedTripType)
      })
    )
  }

  onSaveAll() {
    this.tripType$
    .pipe(take(1))
    .subscribe((tripType) => {
      this.store.dispatch(TripAction.modifyTripType({ tripType }));
    });
  }
  
  changeTripType(changedTripType: IChangeTripType) {
    this.tripType$ = this.tripType$.pipe(
      map((tripTypes) => {
        const { trip, tripIndex } = changedTripType;
        if (tripIndex >= 0 && tripIndex < tripTypes.length) {
          return [...tripTypes.slice(0, tripIndex), trip, ...tripTypes.slice(tripIndex + 1)];
        }
        return tripTypes;
      })
    );
  }

  onRemoveTripType(tripRemoveIndex: number) {
    this.tripType$ = this.tripType$.pipe(
      map((tripTypes) => {
        if (tripRemoveIndex >= 0 && tripRemoveIndex < tripTypes.length) {
          tripTypes.splice(tripRemoveIndex, 1);
        }
        return tripTypes;
      })
    )
  }

  ngOnInit(): void {
    if (!this.isFetching) {
      this.isFetching = true;
      this.store.dispatch(TripAction.getTripType());
    }

    this.tripTypeSubscription = this.tripType$.subscribe((tripType) => {
      this.isFetching = false;
    });
  }

  ngOnDestroy(): void {
    this.tripTypeSubscription.unsubscribe()
  }

  constructor(private store: Store<IAppState>){
    this.tripType$ = this.store.pipe(select(tripTypeSelector))
    this.tripError$ = this.store.pipe(select(tripErrorSelector))
  }
}
