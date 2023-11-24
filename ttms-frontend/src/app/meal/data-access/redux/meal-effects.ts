import { Injectable } from '@angular/core';
import * as MealAction from './meal-actions'
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, mergeMap, of } from 'rxjs';
import { IAppState } from 'src/app/share/data-access/types/app-state.interface';
import { Store } from '@ngrx/store';
import { MealService } from '../services/meal.service';

@Injectable()
export class MealEffect{
    getMealPrice$ = createEffect(() =>
        this.actions$.pipe(
            ofType(MealAction.getMealPrice),
            mergeMap(() => 
                this.mealService.getMealPrice().pipe(
                    map((mealPrice) =>{ 
                        return MealAction.getMealPriceSuccess({ mealPrice })
                    }),
                    catchError((error) => 
                        of(MealAction.getMealPriceFailure({ error }))
                    ) 
                ) 
            ),
            catchError((error) => 
                of(MealAction.getMealPriceFailure({ error }))
            )
        )
    )

    updateMealPrice$= createEffect(() =>
        this.actions$.pipe(
            ofType(MealAction.updateMealPrice),
            mergeMap((action) =>
                this.mealService.updateMeal(action.mealPrice).pipe(
                    map((mealPrice) => MealAction.updateMealPriceSuccess({ mealPrice })),
                    catchError((error) =>
                        of(MealAction.updateMealPriceFailure({ error }))
                    )
                )
            ),
            catchError((error) =>
                of(MealAction.updateMealPriceFailure({ error }))
            )
        )
    )

    constructor(
        private actions$: Actions,
        private mealService: MealService
    ) {}
}