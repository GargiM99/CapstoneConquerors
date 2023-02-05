import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AgentFormsComponent } from './agent-forms/agent-forms.component';
import { AgentListComponent } from './agent-list/agent-list.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AgentService } from '../services/agent.service';


@NgModule({
  declarations: [
    AgentListComponent,
    AgentFormsComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [AgentService]
})
export class AgentComponentsModule { }
 