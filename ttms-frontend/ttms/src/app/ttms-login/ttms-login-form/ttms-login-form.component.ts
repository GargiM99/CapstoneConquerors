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

  constructor (private router : Router, private authService : AuthenticationService){}

  loginDetails = new FormGroup({
    username: new FormControl("", [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(50)
    ]),
    password: new FormControl("", [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(50)
    ])
  })

  loginBtn(){
    this.authService.authenticateUser(<LoginDetails>this.loginDetails.value)
    this.router.navigate(['/']);
  }
}
