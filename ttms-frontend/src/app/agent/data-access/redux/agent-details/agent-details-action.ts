import { createAction, props } from "@ngrx/store"
import { IAgentDetails } from "../../types/agent-details.interface"
import { HttpErrorResponse } from "@angular/common/http"

export const getAgentDetails = createAction(
    '[AgentDetails] Get Agent Details',
    props<{ agentId: number | null }>()
  )
export const getAgentDetailsSuccess = createAction(
    '[AgentDetails] Get Agent Details Success',
    props<{ agentDetails: IAgentDetails }>()
)
export const getAgentDetailsFailure = createAction(
    '[AgentDetails] Get Agent Details Failure',
    props<{ error: HttpErrorResponse | Error | null }>()
)

export const updateAgentDetails = createAction(
    '[AgentDetails] Update Agent Details',
    props<{ agentId: number, agentDetails: IAgentDetails }>()
  )
export const updateAgentDetailsSuccess = createAction(
    '[AgentDetails] Update Agent Details Success',
    props<{ agentDetails: IAgentDetails }>()
)
export const updateAgentDetailsFailure = createAction(
    '[AgentDetails] Update Agent Details Failure',
    props<{ error: HttpErrorResponse | Error | null }>()
)

export const resetAgentPassword = createAction(
    '[AgentDetails] Reset Agent Password',
    props<{ agentId: number }>()
  )
export const resetAgentPasswordSuccess = createAction(
    '[AgentDetails] Reset Agent Password Success',
    props<{ password: string }>()
)
export const resetAgentPasswordFailure = createAction(
    '[AgentDetails] Reset Agent Password Failure',
    props<{ error: HttpErrorResponse | Error | null }>()
)

export const promoteAgent = createAction(
    '[AgentDetails] Promote Agent',
    props<{ agentId: number }>()
  )
export const promoteAgentSuccess = createAction(
    '[AgentDetails] Promote Agent Success',
    props<{ role: string }>()
)
export const promoteAgentFailure = createAction(
    '[AgentDetails] Promote Agent Failure',
    props<{ error: HttpErrorResponse | Error | null }>()
)