import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { IAgentBasics } from '../../data-access/types/agent-basics.interface';
import { Store, select } from '@ngrx/store';
import { IAppState } from 'src/app/share/data-access/types/app-state.interface';
import { agentBasicsSelector, agentErrorSelector, agentIsLoadingSelector } from '../../data-access/redux/agent-selectors';
import * as AgentAction from '../../data-access/redux/agent-actions'
import { TAgentSearch } from '../../data-access/types/agent-search.type';

@Component({
  selector: 'app-view-agent',
  templateUrl: './view-agent.component.html',
  styleUrls: ['./view-agent.component.scss']
})
export class ViewAgentComponent implements OnInit{
  agentIsLoading$: Observable<boolean>
  agentError$: Observable<HttpErrorResponse | Error | null>
  agents$: Observable<IAgentBasics[] | null>

  searchValue: string = ''
  fieldChoice: TAgentSearch = 'firstname'

  constructor(private store: Store<IAppState>){
    this.agentIsLoading$ = this.store.pipe(select(agentIsLoadingSelector))
    this.agentError$ = this.store.pipe(select(agentErrorSelector))
    this.agents$ = this.store.pipe(select(agentBasicsSelector))
  } 

  handleSearch(searchValue: string): void{
    this.searchValue = searchValue;
  }

  handleFieldChange(fieldChoice: TAgentSearch): void{
    this.fieldChoice = fieldChoice
  }

  ngOnInit(): void {
    this.agents$.forEach((agents)=>{
      if (agents == null || agents.length == 0)
        this.store.dispatch(AgentAction.getAgentBasics({agentIds: null}))
    })
  }
}
