import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientDetailsComponent } from './client-details.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [ClientDetailsComponent],
  imports: [CommonModule, ReactiveFormsModule],
  exports: [ClientDetailsComponent]
})
export class ClientDetailsModule { }
