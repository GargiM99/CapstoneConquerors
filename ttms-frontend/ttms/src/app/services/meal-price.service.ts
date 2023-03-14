import { Injectable } from '@angular/core';
import { MealPrices } from '../classes/meal-prices';
import mealPriceData from '../../assets/data/mealPriceData.json'
import endPoints from '../../assets/data/endpoints.json'
import { TokenService } from '../auth/token.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

/*
* author: Hamza
* date: 2023/03/10
* description: Manages the meal prices
*/

@Injectable({
  providedIn: 'root'
})

export class MealPriceService {

  constructor(private tokenService: TokenService, private http: HttpClient) { }

  static readonly MAX_PRICE = 350
  static readonly MIN_PRICE = 0

  private _mealPrices: MealPrices = mealPriceData

  get mealPrices() {
    return this._mealPrices
  } 

  set mealPrices(newMealPrices: MealPrices) {
    if (this.validateMealPrice(newMealPrices))
      this._mealPrices = newMealPrices
  }

  //Saves meals prices to the backend server
  async saveMealPrices(newMealPrices: MealPrices): Promise<boolean> {
    if (this.validateMealPrice(newMealPrices)) {

      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.tokenService.getToken()}`
      });

      let response = this.http.post<any>(endPoints.meal, JSON.stringify(newMealPrices), { headers: headers })

      return new Promise((resolve) => {
        response.subscribe({
          next: (isPriceUpdated) => { resolve(isPriceUpdated.priceUpdated) },
          error: () => { resolve(false) }
        })
      })
    }

    return false
  }

  //Validates meal price just incase price is too high or low
  validateMealPrice(mealPrices: MealPrices) {
    if (mealPrices == null)
      return false

    else if (mealPrices.faPrice > MealPriceService.MAX_PRICE + 100 || mealPrices.faPrice < MealPriceService.MIN_PRICE)
      return false

    else if (mealPrices.fcPrice > MealPriceService.MAX_PRICE + 100 || mealPrices.fcPrice < MealPriceService.MIN_PRICE)
      return false

    else if (mealPrices.qaPrice > MealPriceService.MAX_PRICE || mealPrices.qaPrice < MealPriceService.MIN_PRICE)
      return false

    else if (mealPrices.qcPrice > MealPriceService.MAX_PRICE || mealPrices.qcPrice < MealPriceService.MIN_PRICE)
      return false

    else if (mealPrices.snackPrice > MealPriceService.MAX_PRICE - 50 || mealPrices.snackPrice < MealPriceService.MIN_PRICE)
      return false

    return true
  }
}
