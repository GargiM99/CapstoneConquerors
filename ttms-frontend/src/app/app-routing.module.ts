import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './share/feature/home/home.component';
import { authGuardFn } from './share/data-access/services/auth/auth.guard';
import { LoginComponent } from './share/feature/login/login.component';
import { ViewAgentComponent } from './agent/feature/view-agent/view-agent.component';
import { AddAgentComponent } from './agent/feature/add-agent/add-agent.component';

const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [authGuardFn], data: { role: ['ADMIN', 'AGENT'] }},
  { path: 'login', component: LoginComponent },
  { path: 'agent', component: ViewAgentComponent, canActivate: [authGuardFn], data: { role: ['ADMIN', 'AGENT'] }},
  { path: 'agent/add', component: AddAgentComponent, canActivate: [authGuardFn], data: { role: ['ADMIN'] } } 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
