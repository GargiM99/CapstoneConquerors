import { createReducer, on } from "@ngrx/store";
import { IClientState } from "../types/client-state.interface";
import * as ClientAction from "./client-actions";
import { IClientBasics } from "../types/client-basic.inteface";
import { IClientDetails } from "../types/client-details.interface";

export const intialState: IClientState = {
    isLoading: false,
    clientBasics: [],
    clientId: null,
    clientDetails: null,
    error: null
}

export const clientReducer = createReducer(
    intialState,
    on(ClientAction.getClientBasics, (state, action) => ({
        ...state,
        isLoading: true
    })),
    on(ClientAction.getClientBasicsSuccess, (state, action) => ({
        ...state,
        isLoading: false,
        clientBasics: action.clientBasics ?? [],
        error: null
    })),
    on(ClientAction.getClientBasicsFailure, (state, action) => ({
        ...state,
        isLoading: false,
        error: action.error  
    })),

    on(ClientAction.getClientDetails, (state, action) => ({
        ...state,
        isLoading: true,
        clientId: action.clientId
    })),
    on(ClientAction.getClientDetailsSuccess, (state, action) => ({
        ...state,
        isLoading: false,
        clientDetails: action.clientDetails,
        error: null
    })),
    on(ClientAction.getClientDetailsFailure, (state, action) => ({
        ...state,
        isLoading: false,
        error: action.error  
    })),

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
    })),

    on(ClientAction.updateClient, (state, action) => ({
        ...state,
        isLoading: true,
        clientDetails: action.clientDetails,
        clientId: action.clientId  
    })),
    on(ClientAction.updateClientSuccess, (state, action) => ({
        ...state,
        isLoading: false,
        clientDetails: action.clientDetails,
        clientBasics: updateBasicsById(state.clientBasics, action.clientDetails),
        error: null 
    })),
    on(ClientAction.updateClientFailure, (state, action) => ({
        ...state,
        isLoading: false,
        error: action.error  
    }))
)

const updateBasicsById = (clientBasics: IClientBasics[], clientDetails: IClientDetails) =>{
    const id = clientDetails.user.id
    const clientArr = [...clientBasics]
    const indexToUpdate = clientArr.findIndex(client => client.id === id)
    const clientBasic: IClientBasics = {
        username: clientDetails.user.username,
        id: clientDetails.user.id,
        firstname: clientDetails.person.firstname,
        lastname: clientDetails.person.lastname
    }

    if (indexToUpdate !== -1) 
        clientArr[indexToUpdate] = clientBasic 

    return clientArr
} 