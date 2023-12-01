import { createSelector } from "@ngrx/store";
import { IAppState } from "../../types/app-state.interface";

export const selectFeature = (state:IAppState) => state.profileDetails

export const isLoadingSelector = createSelector(
    selectFeature,
    (state) => state.isLoading
)
export const detailSelector = createSelector(
    selectFeature,
    (state) => state.profileDetail
)
export const usernameSelector = createSelector(
    selectFeature,
    (state) => state.username
)
export const scheduleSelector = createSelector(
    selectFeature,
    (state) => state.profileSchedule
)
export const idSelector = createSelector(
    selectFeature,
    (state) => state.profileId
)
export const authSelector = createSelector(
    selectFeature,
    (state) => state.profileAuthDetails
)
export const errorSelector = createSelector(
    selectFeature,
    (state) => state.error
)
