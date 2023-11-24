import { createReducer, on } from '@ngrx/store'
import { IMealDetailState } from '../types/meal-detail-state.interface'
import * as MealAction from './meal-actions'

export const intialState: IMealDetailState = {
    isLoading: false,
    mealPrice: null,
    error: null
} 

export const mealReducer = createReducer(
    intialState,
    on(MealAction.getMealPrice, (state, action) => ({
        ...state,
        isLoading: true
    })),
    on(MealAction.getMealPriceSuccess, (state, action) => ({
        ...state,
        isLoading: false,
        mealPrice: action.mealPrice,
        error: null
    })),
    on(MealAction.getMealPriceFailure, (state, action) => ({
        ...state,
        isLoading: false,
        error: action.error
    })),

    on(MealAction.updateMealPrice, (state, action) => ({
        ...state,
        isLoading: true,
        mealPrice: action.mealPrice
    })),
    on(MealAction.updateMealPriceSuccess, (state, action) => ({
        ...state,
        isLoading: false,
        mealPrice: action.mealPrice,
        error: null
    })),
    on(MealAction.updateMealPriceFailure, (state, action) => ({
        ...state,
        isLoading: false,
        error: action.error
    }))
)