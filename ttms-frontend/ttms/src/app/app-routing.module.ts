import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TtmsMealFormComponent } from './ttms-meals/ttms-meal-form/ttms-meal-form.component';
import { TtmsLoginFormComponent } from './ttms-login/ttms-login-form/ttms-login-form.component';
import { TtmsDashboardContentComponent } from './ttms-dashboard/ttms-dashboard-content/ttms-dashboard-content.component';
import { canActivateUser, PermissionsService} from './auth/auth-user'


const routes: Routes = [
  { path: '', component: TtmsDashboardContentComponent, canActivate: [canActivateUser] },
  { path: 'meals', component: TtmsMealFormComponent, canActivate: [canActivateUser] },
  { path: 'login', component: TtmsLoginFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [PermissionsService]
})
export class AppRoutingModule { }
