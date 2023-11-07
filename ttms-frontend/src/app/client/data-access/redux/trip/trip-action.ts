import { createAction, props } from "@ngrx/store"
import { ITripEventDetails } from "../../types/trip/trip-event-details.interface"
import { HttpErrorResponse } from "@angular/common/http"
import { ITripCreateDetails } from "../../types/trip/create-trip-details.interface"
import { ITripType } from "../../types/trip/trip-type.interface"

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

export const updateTrip = createAction(
    '[Trip] Update Trip Details',
    props<{ tripDetails: ITripEventDetails }>()
)
export const updateTripSuccess = createAction(
    '[Trip] Update Trip Details Success',
    props<{ tripDetails: ITripEventDetails }>()
)
export const updateTripFailure = createAction(
    '[Trip] Update Trip Details Success',
    props<{ error: HttpErrorResponse | Error | null }>()
)

export const getTripType = createAction(
    '[Trip] Get Trip Type'
)
export const getTripTypeSuccess = createAction(
    '[Trip] Get Trip Type Success',
    props<{ tripType: ITripType[] }>()
)
export const getTripTypeFailure = createAction(
    '[Trip] Get Trip Type Failure',
    props<{ error: HttpErrorResponse | Error | null }>()
)

export const modifyTripType = createAction(
    '[Trip] Modify Trip Type',
    props<{ tripType: ITripType[] }>()
)
export const modifyTripTypeSuccess = createAction(
    '[Trip] Modify Trip Type Success',
    props<{ tripType: ITripType[] }>()
)
export const modifyTripTypeFailure = createAction(
    '[Trip] Modify Trip Type Failure',
    props<{ error: HttpErrorResponse | Error | null }>()
)