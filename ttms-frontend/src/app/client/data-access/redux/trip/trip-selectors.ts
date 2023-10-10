import { createSelector } from "@ngrx/store"
import { IAppState } from "src/app/share/data-access/types/app-state.interface"

export const selectFeature = (state: IAppState) => state.tripDetails

export const tripIsLoadingSelector = createSelector(
    selectFeature,
    (state) =>state.isLoading
)
export const tripDetailsSelector = createSelector(
    selectFeature,
    (state) =>state.tripDetails
)
export const tripListSelector = createSelector(
    selectFeature,
    (state) =>state.tripList
)
export const tripIdSelector = createSelector(
    selectFeature,
    (state) =>state.tripId
)
export const tripCreateDetailsSelector = createSelector(
    selectFeature,
    (state) =>state.tripCreateDetails
)
export const tripErrorSelector = createSelector(
    selectFeature,
    (state) =>state.error
)