import { HttpErrorResponse } from "@angular/common/http"
import { ITripDetails } from "./trip-details.interface"
import { ITripEventDetails } from "./trip-event-details.interface"
import { ITripCreateDetails } from "./create-trip-details.interface"
import { ITripType } from "./trip-type.interface"

export interface ITripState{
    isLoading: boolean
    tripDetails: ITripEventDetails | null
    tripList: ITripDetails[]
    tripCreateDetails: ITripCreateDetails | null
    tripId: number | null
    tripType: ITripType[] 
    error: HttpErrorResponse | Error | null
}