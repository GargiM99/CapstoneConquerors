import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AgentComponentsModule } from './agent-components/agent-components.module';
import { AgentFormsComponent } from './agent-components/agent-forms/agent-forms.component';
import { AgentListComponent } from './agent-components/agent-list/agent-list.component';
import { UserFormComponent } from './components/user-form/user-form.component';
import { UserListComponent } from './components/user-list/user-list.component';

const routes: Routes = [
  { path : 'agent', component: AgentListComponent},
  { path : 'addagent', component: AgentFormsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes),
            AgentComponentsModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
