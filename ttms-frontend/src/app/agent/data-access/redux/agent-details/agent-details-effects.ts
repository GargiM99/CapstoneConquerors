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

    updateAgents$ = createEffect(() =>
        this.actions$.pipe(
            ofType(AgentDetailAction.updateAgentDetails),
            mergeMap((action) =>
                this.agentService.updateAgent(action.agentDetails, action.agentId).pipe(
                    map((agentDetails) => AgentDetailAction.updateAgentDetailsSuccess({ agentDetails })),
                    catchError((error) =>
                        of(AgentDetailAction.updateAgentDetailsFailure({ error: error }))
                    )
                )
            ),
            catchError((error) =>
                of(AgentDetailAction.updateAgentDetailsFailure({ error: error }))
            )
        )
    )

    resetPassword$ = createEffect(() =>
        this.actions$.pipe(
            ofType(AgentDetailAction.resetAgentPassword),
            mergeMap((action) =>              
                this.agentService.resetAgentPassword(action.agentId).pipe(
                    map((password) => AgentDetailAction.resetAgentPasswordSuccess({ password : password.password })),
                    catchError((error) => 
                        of(AgentDetailAction.resetAgentPasswordFailure({ error: error }))
                    )
                )
            ),   
            catchError((error) => 
                of(AgentDetailAction.resetAgentPasswordFailure({ error: error }))
            )
        )
    )

    promoteAgent$ = createEffect(() =>
        this.actions$.pipe(
            ofType(AgentDetailAction.promoteAgent),
            mergeMap((action) =>
                this.agentService.promoteAgent(action.agentId).pipe(
                    map((result) => {
                        if (result.isPromoted == false)
                            return AgentDetailAction.getAgentDetailsFailure({error: new Error("Couldn't promote")})
                        return AgentDetailAction.promoteAgentSuccess({role: "ADMIN"})
                    }),
                    catchError((error) => 
                        of(AgentDetailAction.resetAgentPasswordFailure({ error: error }))
                    )
                )
            ),
            catchError((error) => 
                of(AgentDetailAction.resetAgentPasswordFailure({ error: error }))
            )
        )
    )

    constructor(
        private actions$: Actions,
        private agentService: AgentService
    ) {}
}