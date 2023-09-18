import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AgentDetailsComponent } from './agent-details.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [AgentDetailsComponent],
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  exports: [AgentDetailsComponent]
})
export class AgentDetailsModule { }
