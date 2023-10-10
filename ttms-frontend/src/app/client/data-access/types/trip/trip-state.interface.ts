import { HttpErrorResponse } from "@angular/common/http"
import { ITripDetails } from "./trip-details.interface"
import { ITripEventDetails } from "./trip-event-details.interface"
import { ITripCreateDetails } from "./create-trip-details.interface"

export interface ITripState{
    isLoading: boolean
    tripDetails: ITripEventDetails | null
    tripList: ITripDetails[]
    tripCreateDetails: ITripCreateDetails | null
    tripId: number | null
    error: HttpErrorResponse | Error | null
}