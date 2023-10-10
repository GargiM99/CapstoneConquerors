import { Injectable } from "@angular/core";
import { TripService } from "../../services/trip.service";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { catchError, map, mergeMap, of } from "rxjs";
import * as TripAction from "./trip-action"

@Injectable() 
export class TripEffect{
    getTripDetail$ = createEffect(() =>
        this.actions$.pipe(
            ofType(TripAction.getTripDetails),
            mergeMap((action) =>
                this.tripService.getTripEvent(action.tripId).pipe(
                    map((tripDetails) => TripAction.getTripDetailsSuccess({tripDetails})),
                    catchError((error) =>
                        of(TripAction.getTripDetailsFailure({ error: error }))
                    )
                )
            ),
            catchError((error) =>
                of(TripAction.getTripDetailsFailure({ error: error }))
            )
        )
    )

    createTrip$ = createEffect(() =>
        this.actions$.pipe(
            ofType(TripAction.createTrip),
            mergeMap((action) =>
                this.tripService.createTrip(action.createDetails).pipe(
                    map((tripDetails) => TripAction.createTripSuccess({tripDetails})),
                    catchError((error) =>
                        of(TripAction.createTripFailure({ error }))
                    )
                )
            ), 
            catchError((error) =>
                of(TripAction.createTripFailure({ error }))
            )
        )
    )

    constructor(private actions$: Actions, private tripService: TripService){}
}