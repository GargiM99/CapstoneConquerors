import { createReducer, on } from '@ngrx/store'
import { IProfileDetailState } from '../../types/profile/profile-details-state.interface'
import * as ProfileAction from './profile-actions'

export const intialState: IProfileDetailState = {
    isLoading: false,
    profileDetail: null,
    username: null,
    profileSchedule: [],
    profileId: 0,
    profileAuthDetails: null,
    error: null
} 

export const profileReducers = createReducer(
    intialState,
    on(ProfileAction.getProfileDetails, (state, action) => ({
        ...state,
        isLoading: true,
        username: action.username
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
        isLoading: false
    })),
    on(ProfileAction.updateProfileDetailsFailure, (state, action) => ({
        ...state,
        isLoading: false,
        error: action.error
    })),

    on(ProfileAction.getProfileSchedule, (state, action) => ({
        ...state,
        isLoading: true,
        profileId: action.id
    })),
    on(ProfileAction.getProfileScheduleSuccess, (state, action) => ({
        ...state,
        isLoading: false,
        profileSchedule: action.profileSchedule,
        error: null
    })),
    on(ProfileAction.getProfileScheduleFailure, (state, action) => ({
        ...state,
        isLoading: false,
        error: action.error
    })),

    on(ProfileAction.updatePassword, (state, action) => ({
        ...state,
        isLoading: true,
        profileAuthDetails: action.authDetails
    })),
    on(ProfileAction.updatePasswordSuccess, (state, action) => ({
        ...state,
        isLoading: false,
        profileAuthDetails: action.authDetails,
        error: null
    })),
    on(ProfileAction.updatePasswordFailure, (state, action) => ({
        ...state,
        isLoading: false,
        error: action.error
    }))
)