import { Injectable } from '@angular/core';
import * as AgentDetailAction from './agent-details-action'
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { AgentService } from '../../services/agent.service';
import { catchError, map, mergeMap, of } from 'rxjs';

@Injectable() 
export class AgentDetailsEffect{ 
    getAgents$ = createEffect(() =>
        this.actions$.pipe(
            ofType(AgentDetailAction.getAgentDetails),
            mergeMap((action) =>              
                this.agentService.getAgentDetails(action.agentId).pipe(
                    map((agentDetails) => AgentDetailAction.getAgentDetailsSuccess({ agentDetails })),
                    catchError((error) => 
                        of(AgentDetailAction.getAgentDetailsFailure({ error: error }))
                    )
                )
            ),   
            catchError((error) => 
                of(AgentDetailAction.getAgentDetailsFailure({ error: error }))
            )
        )
    )

    constructor(
        private actions$: Actions,
        private agentService: AgentService
    ) {}
}