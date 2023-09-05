import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { IAgentBasics } from 'src/app/agent/data-access/types/agent-basics.interface';
import { IAppState } from '../../data-access/types/app-state.interface';
import { Store, select } from '@ngrx/store';
import { agentBasicsSelector, agentErrorSelector, agentIsLoadingSelector } from '../../../agent/data-access/redux/agent-selectors';
import * as AgentAction from '../../../agent/data-access/redux/agent-actions'
import { HttpErrorResponse } from '@angular/common/http';
 
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit{
  agentIsLoading$: Observable<boolean>
  agentError$: Observable<HttpErrorResponse | Error | null>
  agents$: Observable<IAgentBasics[] | null>

  constructor(private store: Store<IAppState>){
    this.agentIsLoading$ = this.store.pipe(select(agentIsLoadingSelector))
    this.agentError$ = this.store.pipe(select(agentErrorSelector))
    this.agents$ = this.store.pipe(select(agentBasicsSelector))
  }
  
  ngOnInit(): void {
    this.store.dispatch(AgentAction.getAgentBasics({ agentIds: null }))
  }
}
