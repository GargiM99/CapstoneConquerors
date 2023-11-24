import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Store, select } from '@ngrx/store';
import { IAppState } from 'src/app/share/data-access/types/app-state.interface';
import { mealErrorSelector, mealIsLoadingSelector, mealPriceSelector } from '../../data-access/redux/meal-selectors';
import { Observable, Subscription } from 'rxjs';
import { IMealPrice } from '../../data-access/types/meal-price.interface';
import { HttpErrorResponse } from '@angular/common/http';
import * as MealAction from '../../data-access/redux/meal-actions';

@Component({
  selector: 'app-meal-price',
  templateUrl: './meal-price.component.html',
  styleUrls: ['./meal-price.component.scss']
})
export class MealPriceComponent implements OnInit, OnDestroy{
  mealPrices$: Observable<IMealPrice | null>
  isLoading$: Observable<boolean>
  error$: Observable<HttpErrorResponse | Error | null>

  MAX_PRICE = 1000;
  mealSub!: Subscription

  mealPriceForm = this.fb.group({
    qaPrice: [0, [Validators.required, Validators.max(this.MAX_PRICE), Validators.min(0)]],
    qcPrice: [0, [Validators.required, Validators.max(this.MAX_PRICE), Validators.min(0)]],
    faPrice: [0, [Validators.required, Validators.max(this.MAX_PRICE), Validators.min(0)]],
    fcPrice: [0, [Validators.required, Validators.max(this.MAX_PRICE), Validators.min(0)]],
    snackPrice: [0, [Validators.required, Validators.max(this.MAX_PRICE), Validators.min(0)]]
  })

  isInvalid(fieldName: string): boolean {
    const control = this.mealPriceForm.get(fieldName)
    return control ? control.invalid && (control.dirty || control.touched) : true
  }

  updatePrices(){
    const newMealPrice = <IMealPrice>this.mealPriceForm.value
    this.store.dispatch(MealAction.updateMealPrice({ mealPrice : newMealPrice }))
    this.patchPrice()
  }

  patchPrice(){
    this.mealSub = this.mealPrices$.subscribe((data) => {
      if(data){
        this.mealPriceForm.patchValue({
          qaPrice: data.qaPrice,
          qcPrice: data.qcPrice,
          faPrice: data.faPrice,
          fcPrice: data.fcPrice,
          snackPrice: data.snackPrice
        })
      }
    })
  }

  constructor(private store: Store<IAppState>, private fb: FormBuilder){
    this.mealPrices$ = this.store.pipe(select(mealPriceSelector))
    this.isLoading$ = this.store.pipe(select(mealIsLoadingSelector))
    this.error$ = this.store.pipe(select(mealErrorSelector))
  }

  ngOnDestroy(): void {
    this.mealSub.unsubscribe()
  }

  ngOnInit(): void {
    this.store.dispatch(MealAction.getMealPrice())
    this.patchPrice()
  }
} 
