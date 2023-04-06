import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TtmsCommonModule } from './ttms-common/ttms-common.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TtmsMealsModule } from './ttms-meals/ttms-meals.module';
import { MealPriceService } from './services/meal-price.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { TtmsLoginModule } from './ttms-login/ttms-login.module';
import { TokenService } from './auth/token.service';
import { JwtHelperService, JwtModule, JWT_OPTIONS } from '@auth0/angular-jwt';
import { TtmsAgentsModule } from './ttms-agents/ttms-agents.module';
import { TtmsDashboardModule } from './ttms-dashboard/ttms-dashboard.module';
import { TtmsProfileModule } from './ttms-profile/ttms-profile.module';

export function jwtOptionsFactory() {
  return {
    tokenGetter: () => {return localStorage.getItem('jwtoken')}
  };
}

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    TtmsCommonModule,
    NgbModule,
    TtmsMealsModule,
    TtmsLoginModule,
    HttpClientModule,
    TtmsAgentsModule,
    TtmsDashboardModule,
    TtmsProfileModule,
    JwtModule.forRoot({jwtOptionsProvider: {provide: JWT_OPTIONS, useFactory: jwtOptionsFactory,},}),
  ],
  providers: [
    MealPriceService,
    TokenService,
    HttpClient,
    JwtHelperService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
