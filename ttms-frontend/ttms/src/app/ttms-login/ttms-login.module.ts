import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TtmsLoginFormComponent } from './ttms-login-form/ttms-login-form.component';
import { AuthenticationService } from '../auth/authentication.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    TtmsLoginFormComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  providers: [
    AuthenticationService
  ]
})
export class TtmsLoginModule { }
