import { inject, NgModule } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, RouterModule, RouterStateSnapshot, Routes } from '@angular/router';
import { TtmsMealFormComponent } from './ttms-meals/ttms-meal-form/ttms-meal-form.component';
import { TtmsLoginFormComponent } from './ttms-login/ttms-login-form/ttms-login-form.component';
import { TtmsDashboardContentComponent } from './ttms-dashboard/ttms-dashboard-content/ttms-dashboard-content.component';
import { TokenService } from './auth/token.service';
import { TtmsAgentFormComponent } from './ttms-agents/ttms-agent-form/ttms-agent-form.component';
import { TtmsAgentListComponent } from './ttms-agents/ttms-agent-list/ttms-agent-list.component';
import { TtmsPasswordFormComponent } from './ttms-profile/ttms-password-form/ttms-password-form.component';
import { TtmsProfileViewComponent } from './ttms-profile/ttms-profile-view/ttms-profile-view.component';

//Functions for checking if user's token is expired or is not null
const canActivateUser : CanActivateFn =
    (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
        return inject(TokenService).canActivate();
    };

const routes: Routes = [
  { path: '', component: TtmsDashboardContentComponent, canActivate: [canActivateUser] },
  { path: 'meals', component: TtmsMealFormComponent, canActivate: [canActivateUser] },
  { path: 'agent/add', component: TtmsAgentFormComponent, canActivate: [canActivateUser]},
  { path: 'agent', component: TtmsAgentListComponent, canActivate: [canActivateUser]},
  { path: 'password/change', component:TtmsPasswordFormComponent, canActivate: [canActivateUser]},
  { path: 'profile', component:TtmsProfileViewComponent, canActivate: [canActivateUser]},
  { path: 'login', component: TtmsLoginFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
