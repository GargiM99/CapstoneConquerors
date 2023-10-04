import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreateClientComponent } from './create-client.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [CreateClientComponent],
  imports: [CommonModule, ReactiveFormsModule]
})
export class CreateClientModule { }
