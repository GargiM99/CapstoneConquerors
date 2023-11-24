import { createAction, props } from '@ngrx/store';
import { IMealPrice } from '../types/meal-price.interface';
import { HttpErrorResponse } from '@angular/common/http';

export const getMealPrice = createAction(
  '[MealPrice] Get Meal Price'
)
export const getMealPriceSuccess = createAction(
  '[MealPrice] Get Meal Price Success',
  props<{ mealPrice: IMealPrice | null }>()
)
export const getMealPriceFailure = createAction(
  '[MealPrice] Get Meal Price Failure',
  props<{ error: HttpErrorResponse | Error | null }>()
)

export const updateMealPrice = createAction(
   '[MealPrice] Update Meal Price',
   props<{ mealPrice: IMealPrice }>()
)
export const updateMealPriceSuccess = createAction(
   '[MealPrice] Update Meal Price Success',
   props<{ mealPrice: IMealPrice }>()
)
export const updateMealPriceFailure = createAction(
  '[MealPrice] Update Meal Price Failure',
  props<{ error: HttpErrorResponse | Error | null }>()
)