import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ttms-login-form',
  templateUrl: './ttms-login-form.component.html',
  styleUrls: ['./ttms-login-form.component.scss']
})
export class TtmsLoginFormComponent {

  constructor (private router : Router){}

  loginBtn(){
    localStorage.setItem("token", "test")
    this.router.navigate(['/']);
  }
}
