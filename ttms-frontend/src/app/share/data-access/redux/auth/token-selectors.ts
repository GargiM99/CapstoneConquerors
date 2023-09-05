import { createSelector } from "@ngrx/store";
import { IAppState } from "../../types/app-state.interface";

export const selectFeature = (state: IAppState) => state.tokenDetails

export const isLoadingSelector = createSelector(
    selectFeature,
    (state) =>state.isLoading
)
export const detailSelector = createSelector(
    selectFeature,
    (state) =>state.tokenDetail
)
export const errorSelector = createSelector(
    selectFeature,
    (state) => state.error
)