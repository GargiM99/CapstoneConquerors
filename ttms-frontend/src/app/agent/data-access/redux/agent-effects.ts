import { Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { AgentService } from "../services/agent.service";
import * as AgentAction from './agent-actions'
import { catchError, map, mergeMap, of } from "rxjs";

@Injectable() 
export class AgentBasicsEffect{
    getAgents$ = createEffect(() =>
        this.actions$.pipe(
            ofType(AgentAction.getAgentBasics),
            mergeMap((action) =>
                this.agentService.getAgentBasics(action.agentIds).pipe(
                    map((agentBasics) => AgentAction.getAgentBasicsSuccess({ agentBasics })),
                    catchError((error) => 
                        of(AgentAction.getAgentBasicsFailure({ error: error }))
                    )
                ) 
            ),   
            catchError((error) => 
                of(AgentAction.getAgentBasicsFailure({ error: error.message }))
            )
        )
    )

    addAgent$ = createEffect(() =>
        this.actions$.pipe(
            ofType(AgentAction.addAgent),
            mergeMap((action) =>
                this.agentService.addAgent(action.profileDetails).pipe(
                    map((agentAddAction) => AgentAction.addAgentSuccess({ 
                        agentBasics: agentAddAction.agentBasic, 
                        profileDetails: agentAddAction.profile
                    })),
                    catchError((error) =>
                        of(AgentAction.addAgentFailure({ error: error }))
                    )
                )
            ),
            catchError((error) =>
                of(AgentAction.addAgentFailure({ error: error }))
            )
        )
    )

    constructor(
        private actions$: Actions,
        private agentService: AgentService
    ) {}
}