import { createSelector } from "@ngrx/store";
import { IAppState } from "src/app/share/data-access/types/app-state.interface";

export const selectFeature = (state:IAppState) => state.mealDetails

export const mealIsLoadingSelector = createSelector(
    selectFeature,
    (state) => state.isLoading
)
export const mealPriceSelector = createSelector(
    selectFeature,
    (state) => state.mealPrice
)
export const mealErrorSelector = createSelector(
    selectFeature,
    (state) => state.error
)
