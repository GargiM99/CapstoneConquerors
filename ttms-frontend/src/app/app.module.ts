import { NgModule, isDevMode } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { TokenEffect } from './share/data-access/redux/auth/token-effects';
import { tokenReducers } from './share/data-access/redux/auth/token-reducers';
import { LoginModule } from './share/feature/login/login.module';
import { agentBasicReducer } from './agent/data-access/redux/agent-reducers';
import { AgentBasicsEffect } from './agent/data-access/redux/agent-effects';
import { profileReducers } from './share/data-access/redux/profile/profile-reducers';
import { ProfileEffect } from './share/data-access/redux/profile/profile-effects';
import { HttpClientModule } from '@angular/common/http';
import { HomeModule } from './share/feature/home/home.module';
import { AddAgentModule } from './agent/feature/add-agent/add-agent.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ViewAgentModule } from './agent/feature/view-agent/view-agent.module';
import { PersistentModule } from './share/ui/persistent/persistent.module';
import { CommonModule } from '@angular/common';
import { LoadingScreenComponent } from './share/ui/loading-screen/loading-screen.component';
import { agentDetailReducer } from './agent/data-access/redux/agent-details/agent-details-reducers';
import { AgentDetailsEffect } from './agent/data-access/redux/agent-details/agent-details-effects';
import { AgentDetailsModule } from './agent/feature/agent-details/agent-details.module';
import { ProfileDetailsModule } from './profile/feature/profile-details/profile-details.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    LoginModule,
    HttpClientModule,
    HomeModule,
    AddAgentModule,
    ViewAgentModule,
    PersistentModule,
    AgentDetailsModule,
    ProfileDetailsModule,
    StoreModule.forRoot({
      'tokenDetails': tokenReducers, 
      'agentBasics': agentBasicReducer,
      'agentDetails': agentDetailReducer,
      'profileDetails': profileReducers
    }),
    EffectsModule.forRoot([TokenEffect, AgentBasicsEffect, ProfileEffect, AgentDetailsEffect]),
    StoreDevtoolsModule.instrument({ maxAge: 25, logOnly: !isDevMode() }),
    BrowserAnimationsModule,
    LoadingScreenComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
