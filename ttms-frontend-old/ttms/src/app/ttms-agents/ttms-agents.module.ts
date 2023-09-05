import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TtmsAgentFormComponent } from './ttms-agent-form/ttms-agent-form.component';
import { TtmsAgentListComponent } from './ttms-agent-list/ttms-agent-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    TtmsAgentFormComponent,
    TtmsAgentListComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    TtmsAgentFormComponent,
    TtmsAgentListComponent
  ]
})
export class TtmsAgentsModule { }
