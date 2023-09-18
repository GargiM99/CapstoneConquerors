import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AgentDetailsComponent } from './agent-details.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [AgentDetailsComponent],
  imports: [CommonModule, ReactiveFormsModule],
  exports: [AgentDetailsComponent]
})
export class AgentDetailsModule { }
