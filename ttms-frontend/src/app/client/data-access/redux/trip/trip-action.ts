import { createAction, props } from "@ngrx/store"
import { ITripEventDetails } from "../../types/trip/trip-event-details.interface"
import { HttpErrorResponse } from "@angular/common/http"
import { ITripCreateDetails } from "../../types/trip/create-trip-details.interface"

export const getTripDetails = createAction(
    '[Trip] Get Trip Details',
    props<{ tripId: number }>()
)
export const getTripDetailsSuccess = createAction(
    '[Trip] Get Trip Details Success', 
    props<{ tripDetails: ITripEventDetails | null }>()
)
export const getTripDetailsFailure = createAction(
    '[Trip] Get Trip Details Failure',
    props<{ error: HttpErrorResponse | Error | null }>()
)

export const createTrip = createAction(
    '[Trip] Create Trip Details',
    props<{ createDetails: ITripCreateDetails }>()
)
export const createTripSuccess = createAction(
    '[Trip] Create Trip Details Success',
    props<{ tripDetails: ITripEventDetails }>()
)
export const createTripFailure = createAction(
    '[Trip] Create Trip Details Failure',
    props<{ error: HttpErrorResponse | Error | null }>()
)