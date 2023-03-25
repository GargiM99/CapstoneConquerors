import { inject, NgModule } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, RouterModule, RouterStateSnapshot, Routes } from '@angular/router';
import { TtmsMealFormComponent } from './ttms-meals/ttms-meal-form/ttms-meal-form.component';
import { TtmsLoginFormComponent } from './ttms-login/ttms-login-form/ttms-login-form.component';
import { TtmsDashboardContentComponent } from './ttms-dashboard/ttms-dashboard-content/ttms-dashboard-content.component';
import { TokenService } from './auth/token.service';
import { TtmsAgentFormComponent } from './ttms-agents/ttms-agent-form/ttms-agent-form.component';

//Functions for checking if user's token is expired or is not null
const canActivateUser : CanActivateFn =
    (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
        return inject(TokenService).canActivate();
    };

const routes: Routes = [
  { path: '', component: TtmsDashboardContentComponent, canActivate: [canActivateUser] },
  { path: 'meals', component: TtmsMealFormComponent, canActivate: [canActivateUser] },
  { path: 'agent/add', component: TtmsAgentFormComponent},
  { path: 'login', component: TtmsLoginFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
