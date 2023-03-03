import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TtmsCommonModule } from './ttms-common/ttms-common.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TtmsMealsModule } from './ttms-meals/ttms-meals.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    TtmsCommonModule,
    NgbModule,
    TtmsMealsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
