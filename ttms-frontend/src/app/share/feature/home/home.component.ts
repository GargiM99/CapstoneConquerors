import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { IAgentBasics } from 'src/app/agent/data-access/types/agent-basics.interface';
import { IAppState } from '../../data-access/types/app-state.interface';
import { Store, select } from '@ngrx/store';
import { agentBasicsSelector, agentErrorSelector, agentIsLoadingSelector } from '../../../agent/data-access/redux/agent-selectors';
import { HttpErrorResponse } from '@angular/common/http';
import { clientBasicsSelector, clientErrorSelector, clientIsLoadingSelector } from 'src/app/client/data-access/redux/client/client-selectors';
import { IClientBasics } from 'src/app/client/data-access/types/client/client-basic.inteface';
import * as AgentAction from '../../../agent/data-access/redux/agent-actions';
import * as ClientAction from '../../../client/data-access/redux/client/client-actions';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit{
  agentIsLoading$: Observable<boolean>
  agentError$: Observable<HttpErrorResponse | Error | null>
  agents$: Observable<IAgentBasics[] | null>

  clientIsLoading$: Observable<boolean>
  clientError$: Observable<HttpErrorResponse | Error | null>
  clients$: Observable<IClientBasics[] | null>

  viewTripType(){
    this.router.navigate([`trip/type`])
  }

  constructor(private store: Store<IAppState>, private router: Router){
    this.agentIsLoading$ = this.store.pipe(select(agentIsLoadingSelector))
    this.agentError$ = this.store.pipe(select(agentErrorSelector))
    this.agents$ = this.store.pipe(select(agentBasicsSelector))

    this.clientIsLoading$ = this.store.pipe(select(clientIsLoadingSelector))
    this.clientError$ = this.store.pipe(select(clientErrorSelector))
    this.clients$ = this.store.pipe(select(clientBasicsSelector))
  }
  
  ngOnInit(): void {
    this.store.dispatch(AgentAction.getAgentBasics({ agentIds: null }))
    this.store.dispatch(ClientAction.getClientBasics())
  }
}
