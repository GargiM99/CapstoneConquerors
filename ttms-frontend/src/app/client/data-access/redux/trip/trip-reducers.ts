import { createReducer, on } from "@ngrx/store";
import { ITripState } from "../../types/trip/trip-state.interface";
import * as TripAction from "./trip-action";

export const intialState: ITripState = {
    isLoading: false,
    tripId: null,
    tripDetails: null,
    tripList: [],
    tripType: [],
    tripCreateDetails: null,
    error: null
}

export const tripReducer = createReducer(
    intialState,
    on(TripAction.getTripDetails, (state, action) => ({
        ...state,
        isLoading: true,
        tripId: action.tripId
    })),
    on(TripAction.getTripDetailsSuccess, (state, action) => ({
        ...state,
        isLoading: false,
        tripDetails: action.tripDetails,
        error: null
    })),
    on(TripAction.getTripDetailsFailure, (state, action) => ({
        ...state,
        isLoading: false,
        error: action.error  
    })),

    on(TripAction.createTrip, (state, action) => ({
        ...state,
        isLoading: true,
        tripCreateDetails: action.createDetails
    })),
    on(TripAction.createTripSuccess, (state, action) => ({
        ...state,
        isLoading: false,
        tripDetails: action.tripDetails,
        error: null 
    })),
    on(TripAction.createTripFailure, (state, action) => ({
        ...state,
        isLoading: false,
        error: action.error 
    })),

    on(TripAction.updateTrip, (state, action) => ({
        ...state,
        isLoading: true,
        tripDetails: action.tripDetails
    })),
    on(TripAction.updateTripSuccess, (state, action) => ({
        ...state,
        isLoading: false,
        tripDetails: action.tripDetails,
        error: null 
    })),
    on(TripAction.updateTripFailure, (state, action) => ({
        ...state,
        isLoading: false,
        error: action.error 
    })),

    on(TripAction.getTripType, (state) => ({
        ...state,
        isLoading: true
    })),
    on(TripAction.getTripTypeSuccess, (state, action) => ({
        ...state,
        isLoading: false,
        tripType: action.tripType
    })),
    on(TripAction.getTripTypeFailure, (state, action) => ({
        ...state,
        isLoading: false,
        error: action.error 
    })),

    on(TripAction.modifyTripType, (state, action) => ({
        ...state,
        isLoading: true,
        tripType: action.tripType,
        error: null
    })),
    on(TripAction.modifyTripTypeSuccess, (state, action) => ({
        ...state,
        isLoading: false,
        tripType: action.tripType,
        error: null
    })),
    on(TripAction.modifyTripTypeFailure, (state, action) => ({
        ...state,
        isLoading: false,
        error: action.error 
    }))
)