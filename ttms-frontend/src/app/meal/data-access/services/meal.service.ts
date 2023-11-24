import { Injectable } from '@angular/core';
import endPoints from '../../../../assets/data/endpoints.json'
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, mergeMap } from 'rxjs';
import { IMealPrice } from '../types/meal-price.interface';
import { ITokenDetail } from 'src/app/share/data-access/types/auth/token-details.interface';
import { IAppState } from 'src/app/share/data-access/types/app-state.interface';
import { Store, select } from '@ngrx/store';
import { Router } from '@angular/router';
import { detailSelector } from 'src/app/share/data-access/redux/auth/token-selectors';

@Injectable({
  providedIn: 'root'
})
export class MealService {
  tokenDetails$: Observable<ITokenDetail | null>

  getMealPrice(): Observable<IMealPrice>{
    return this.tokenDetails$.pipe(
      mergeMap((tokenDetails) => {
        if (tokenDetails == null || tokenDetails.token == null){
          this.router.navigate(['login'])
          throw new Error("Session Expired")
        }
         
        return this.sendGetMeal(tokenDetails.token)
      })
    )
  }

  sendGetMeal(token: string){
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    })
    return  this.http.get<IMealPrice>(endPoints.meal, { headers: headers })
  }

  updateMeal(mealDetails: IMealPrice): Observable<IMealPrice>{
    return this.tokenDetails$.pipe(
      mergeMap((tokenDetails) => {
        if (tokenDetails == null || tokenDetails.token == null){
          this.router.navigate(['login'])
          throw new Error("Session Expired")
        }
         
        return this.sendUpdateMeal(mealDetails, tokenDetails!.token)
      })
    )
  }

  sendUpdateMeal(mealDetails: IMealPrice, token: string){
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    })

    return this.http.put<IMealPrice>(
      endPoints.meal, 
      JSON.stringify(mealDetails), 
      { headers: headers })
  }

  constructor(private http: HttpClient, private store: Store<IAppState>, private router: Router) { 
    this.tokenDetails$ = this.store.pipe(select(detailSelector))
  }
} 
