import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/auth/authentication.service';
import { LoginDetails } from 'src/app/classes/login-details';

@Component({
  selector: 'app-ttms-login-form',
  templateUrl: './ttms-login-form.component.html',
  styleUrls: ['./ttms-login-form.component.scss']
})
export class TtmsLoginFormComponent {

  isAuthenticated : boolean = true;

  constructor (private router : Router, private authService : AuthenticationService){}

  loginDetails = new FormGroup({
    username: new FormControl("", [
      Validators.required,
      Validators.minLength(3)
    ]),
    password: new FormControl("", [
      Validators.required,
      Validators.minLength(3)
    ])
  })

  //Functions for login button which authenticates user
  loginBtn(){
    this.authService.authenticateUser(<LoginDetails>this.loginDetails.value).then(
      (isAuthenticated) => {
        this.isAuthenticated = isAuthenticated 
        this.router.navigate(['/']);
      }
    )
  }
}
