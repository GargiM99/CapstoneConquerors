import { createReducer, on } from "@ngrx/store";
import { IClientState } from "../types/client-state.interface";
import * as ClientAction from "./client-actions";

export const intialState: IClientState = {
    isLoading: false,
    clientBasics: [],
    clientId: null,
    clientDetails: null,
    error: null
}

export const clientReducer = createReducer(
    intialState,
    on(ClientAction.createClient, (state, action) => ({
        ...state,
        isLoading: true,
        clientDetails: action.clientDetails   
    })),
    on(ClientAction.createClientSuccess, (state, action) => ({
        ...state,
        isLoading: false,
        clientBasics: [...state.clientBasics ?? [], action.clientBasics],
        error: null 
    })),
    on(ClientAction.createClientFailure, (state, action) => ({
        ...state,
        isLoading: false,
        error: action.error  
    }))
)