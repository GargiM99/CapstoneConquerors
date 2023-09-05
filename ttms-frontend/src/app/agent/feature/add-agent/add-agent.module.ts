import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddAgentComponent } from './add-agent.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [AddAgentComponent],
  imports: [CommonModule, ReactiveFormsModule],
  exports: [AddAgentComponent]
})
export class AddAgentModule { }
