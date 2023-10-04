import { createAction, props } from '@ngrx/store'
import { IAgentBasics } from '../types/agent-basics.interface'
import { IProfileDetails } from 'src/app/share/data-access/types/profile/profile-details.interface'
import { HttpErrorResponse } from '@angular/common/http'

export const getAgentBasics = createAction(
  '[AgentBasics] Get Agent Basics',
  props<{ agentIds: number[] | null }>()
)
export const getAgentBasicsSuccess = createAction(
  '[AgentBasics] Get Agent Basics Success',
  props<{ agentBasics: IAgentBasics[] | null }>()
)
export const getAgentBasicsFailure = createAction(
  '[AgentBasics] Get Agent Basics Failure', 
  props<{ error: HttpErrorResponse | Error | null }>()
)

export const addAgent = createAction(
  '[Agent] Add Agent',
  props<{ profileDetails: IProfileDetails }>()
)
export const addAgentSuccess = createAction(
  '[Agent] Add Agent Success',
  props<{ profileDetails: IProfileDetails, agentBasics: IAgentBasics }>()
)
export const addAgentFailure = createAction(
  '[Agent] Add Agent Failure',
  props<{ error: HttpErrorResponse | Error | null }>()
)