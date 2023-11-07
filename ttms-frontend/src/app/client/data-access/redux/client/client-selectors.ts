import { createSelector } from "@ngrx/store";
import { IAppState } from "src/app/share/data-access/types/app-state.interface";

export const selectFeature = (state: IAppState) => state.clientDetails

export const clientIsLoadingSelector = createSelector(
    selectFeature,
    (state) =>state.isLoading
)
export const clientDetailsSelector = createSelector(
    selectFeature,
    (state) =>state.clientDetails
)
export const clientBasicsSelector = createSelector(
    selectFeature,
    (state) =>state.clientBasics
)
export const clientIdSelector = createSelector(
    selectFeature,
    (state) =>state.clientId
)
export const clientErrorSelector = createSelector(
    selectFeature,
    (state) =>state.error
)