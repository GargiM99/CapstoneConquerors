import { createReducer, on } from '@ngrx/store'
import { ITokenDetailState } from '../../types/auth/token-details-state.interface'
import * as TokenAction from './token-actions'

export const intialState: ITokenDetailState = {
    isLoading: false,
    tokenDetail: null,
    loginDetail: null,
    error: null
} 

export const tokenReducers = createReducer(
    intialState,
    on(TokenAction.getTokenDetails, (state, action) => ({
        ...state,
        isLoading: true,
        loginDetails: action.loginDetails
    })),
    on(TokenAction.getTokenDetailsSuccess, (state, action) => ({
        ...state,
        isLoading: false,
        tokenDetail: action.tokenDetails
    })),
    on(TokenAction.getTokenDetailsFailure, (state, action) => ({
        ...state,
        isLoading: false,
        error: action.error
    }))
) 