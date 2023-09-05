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
export const idSelector = createSelector(
    selectFeature,
    (state) => state.username
)
export const errorSelector = createSelector(
    selectFeature,
    (state) => state.error
)
