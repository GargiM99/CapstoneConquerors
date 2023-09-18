import { createSelector } from "@ngrx/store"
import { IAppState } from "src/app/share/data-access/types/app-state.interface"

export const selectFeature = (state: IAppState) => state.agentDetails

export const agentDetailIsLoadingSelector = createSelector(
    selectFeature,
    (state) =>state.isLoading
)
export const agentDetailErrorSelector = createSelector(
    selectFeature,
    (state) =>state.error
)
export const agentDetailsSelector = createSelector(
    selectFeature,
    (state) =>state.agentDetails[state.agentId ?? 0]
)
