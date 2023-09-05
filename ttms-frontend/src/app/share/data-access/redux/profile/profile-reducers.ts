import { createReducer, on } from '@ngrx/store'
import { IProfileDetailState } from '../../types/profile/profile-details-state.interface'
import * as ProfileAction from './profile-actions'

export const intialState: IProfileDetailState = {
    isLoading: false,
    profileDetail: null,
    username: null,
    error: null
} 

export const profileReducers = createReducer(
    intialState,
    on(ProfileAction.getProfileDetails, (state, action) => ({
        ...state,
        isLoading: true,
        profileId: action.username
    })),
    on(ProfileAction.getProfileDetailsSuccess, (state, action) => ({
        ...state,
        isLoading: false,
        profileDetail: action.profileDetails
    })),
    on(ProfileAction.getProfileDetailsFailure, (state, action) => ({
        ...state,
        isLoading: false,
        error: action.error
    })),

    on(ProfileAction.updateProfileDetails, (state, action) => ({
        ...state,
        isLoading: true,
        profileDetail: action.profileDetails
    })),
    on(ProfileAction.updateProfileDetailsSuccess, (state, action) => ({
        ...state,
        isLoading: false,
        profileDetail: action.profileDetails
    })),
    on(ProfileAction.updateProfileDetailsFailure, (state, action) => ({
        ...state,
        isLoading: false,
        error: action.error
    }))
)