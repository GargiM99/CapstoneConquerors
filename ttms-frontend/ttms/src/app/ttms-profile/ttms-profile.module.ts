import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TtmsPasswordFormComponent } from './ttms-password-form/ttms-password-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TtmsProfileViewComponent } from './ttms-profile-view/ttms-profile-view.component';

@NgModule({
  declarations: [
    TtmsPasswordFormComponent,
    TtmsProfileViewComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule
  ],
  exports: [
    TtmsPasswordFormComponent,
    TtmsProfileViewComponent
  ]
})
export class TtmsProfileModule { }
