import { Component, OnInit } from '@angular/core';
import { IAppState } from '../../data-access/types/app-state.interface';
import { Store, select } from '@ngrx/store';
import { Observable } from 'rxjs';
import { ITokenDetail } from '../../data-access/types/auth/token-details.interface';
import { detailSelector, errorSelector, isLoadingSelector } from '../../data-access/redux/auth/token-selectors';
import * as TokenAction from '../../data-access/redux/auth/token-actions'
import { FormBuilder, Validators } from '@angular/forms';
import { ILoginDetails } from '../../data-access/types/auth/login-details.interface';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit{
  isLoading$: Observable<boolean> 
  error$: Observable<Error | HttpErrorResponse | null>
  tokenDetails$: Observable<ITokenDetail | null>
 
  loginForm = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  })

  onLogin(){
    let loginDetails = <ILoginDetails>this.loginForm.value
    this.store.dispatch(TokenAction.getTokenDetails({ loginDetails: loginDetails }))
    this.tokenDetails$.subscribe((details) => { 
      if (details != null)
        this.route.navigate([''])
    })
  }

  // Helper method for login form
  isInvalid(controlName: string): boolean {
    const control = this.loginForm.get(controlName);
    return control ? control.invalid && (control.dirty || control.touched) : true;
  }

  ngOnInit(): void {}

  constructor(private store: Store<IAppState>, private fb: FormBuilder, private route: Router){
    this.isLoading$ = this.store.pipe(select(isLoadingSelector))
    this.error$ = this.store.pipe(select(errorSelector))
    this.tokenDetails$ = this.store.pipe(select(detailSelector))
  } 
}
