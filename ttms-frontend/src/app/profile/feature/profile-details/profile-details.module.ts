import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileDetailsComponent } from './profile-details.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [ProfileDetailsComponent],
  imports: [CommonModule, ReactiveFormsModule],
  exports: [ProfileDetailsComponent]
})
export class ProfileDetailsModule { }
