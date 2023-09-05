import { createSelector } from "@ngrx/store";
import { IAppState } from "../../../share/data-access/types/app-state.interface";

export const selectFeature = (state: IAppState) => state.agentBasics

export const agentIsLoadingSelector = createSelector(
    selectFeature,
    (state) =>state.isLoading
)
export const agentErrorSelector = createSelector(
    selectFeature,
    (state) =>state.error
)
export const agentBasicsSelector = createSelector(
    selectFeature,
    (state) =>state.agentBasics
)
export const agentProfileSelector = createSelector(
    selectFeature,
    (state) =>state.newProfile
)
