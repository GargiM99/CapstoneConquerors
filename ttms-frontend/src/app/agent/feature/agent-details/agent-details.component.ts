import { Component, OnInit } from '@angular/core';
import * as AgentDetailAction from '../../data-access/redux/agent-details/agent-details-action'
import { IAgentDetails } from '../../data-access/types/agent-details.interface';
import { IAppState } from 'src/app/share/data-access/types/app-state.interface';
import { Store, select } from '@ngrx/store';
import { agentDetailErrorSelector, agentDetailIsLoadingSelector, agentDetailsSelector } from '../../data-access/redux/agent-details/agent-details-selectors';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'agent-details',
  templateUrl: './agent-details.component.html',
  styleUrls: ['./agent-details.component.scss']
})
export class AgentDetailsComponent implements OnInit{
  agentId: number
  agentDetails$: Observable<IAgentDetails>
  agentError$: Observable<Error | HttpErrorResponse | null>
  agentIsLoading$: Observable<boolean>
  
  ngOnInit(): void {
    this.store.dispatch(AgentDetailAction.getAgentDetails({ agentId: this.agentId }))
  }

  constructor(private store: Store<IAppState>, private route: ActivatedRoute){
    this.agentId = +(this.route.snapshot.paramMap.get('id') ?? 0)

    this.agentIsLoading$ = this.store.pipe(select(agentDetailIsLoadingSelector))
    this.agentError$ = this.store.pipe(select(agentDetailErrorSelector))
    this.agentDetails$ = this.store.pipe(select(agentDetailsSelector))
  }
}
