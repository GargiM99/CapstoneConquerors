import { HttpErrorResponse } from "@angular/common/http"
import { IMealPrice } from "./meal-price.interface"

export interface IMealDetailState{
    isLoading: boolean
    mealPrice: IMealPrice | null
    error: HttpErrorResponse | Error | null
}