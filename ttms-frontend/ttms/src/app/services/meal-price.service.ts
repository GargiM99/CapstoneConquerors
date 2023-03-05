import { Injectable } from '@angular/core';
import { MealPrices } from '../classes/meal-prices';
import mealPriceData from '../../assets/data/mealPriceData.json'

@Injectable({
  providedIn: 'root'
})

export class MealPriceService {

  static readonly MAX_PRICE = mealPriceData.MAX_PRICE
  static readonly MIN_PRICE = mealPriceData.MIN_PRICE

  private _mealPrices: MealPrices = mealPriceData.mealPrice

  get mealPrices() {
    return this._mealPrices
  }

  set mealPrices(newMealPrices: MealPrices) {
    if (this.validateMealPrice(newMealPrices))
      this._mealPrices = newMealPrices
  }

  //Saves meals prices to the backend server
  saveMealPrices(newMealPrices: MealPrices){
    if (this.validateMealPrice(newMealPrices))
      return true

    else
      return false
    //TODO: Send a POST request to server to save data
  }

  //Formats prices with 2 decimal points
  formatPriceJSON(newMealPrices : MealPrices){
    return{
      "qaPrice": (Math.round(newMealPrices.qaPrice * 100) / 100).toFixed(2),
      "qcPrice": (Math.round(newMealPrices.qcPrice * 100) / 100).toFixed(2),
      "faPrice": (Math.round(newMealPrices.faPrice * 100) / 100).toFixed(2),
      "fcPrice": (Math.round(newMealPrices.fcPrice * 100) / 100).toFixed(2),
      "snackPrice": (Math.round(newMealPrices.snackPrice * 100) / 100).toFixed(2)
    }
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

  constructor() { }
}
