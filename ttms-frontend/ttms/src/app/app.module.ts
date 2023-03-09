import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TtmsCommonModule } from './ttms-common/ttms-common.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TtmsMealsModule } from './ttms-meals/ttms-meals.module';
import { MealPriceService } from './services/meal-price.service';
import { AuthenticationService } from './auth/authentication.service';
import { HttpClient, HttpClientModule, HttpHandler, HttpHeaders } from '@angular/common/http';
import { TtmsLoginModule } from './ttms-login/ttms-login.module';

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
    HttpClientModule
  ],
  providers: [
    MealPriceService,
    HttpClient
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
