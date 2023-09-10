import { createReducer, on } from '@ngrx/store'
import { IAgentState } from '../types/agent-basics-state.interface'
import * as AgentAction from './agent-actions'
 
export const intialState: IAgentState = {
    isLoading: false,
    agentBasics: [],
    agentIds: null,
    newProfile: null,
    error: null
}

export const agentBasicReducer = createReducer(
    intialState,
    on(AgentAction.getAgentBasics, (state, action) => ({
      ...state,
      isLoading: true,
      agentIds: action.agentIds   
    })),
    on(AgentAction.getAgentBasicsSuccess, (state, action) => ({
        ...state,
        isLoading: false,
        agentBasics: action.agentBasics ?? []   
    })),
    on(AgentAction.getAgentBasicsFailure, (state, action) => ({
        ...state,
        isLoading: false,
        error: action.error  
    })),

    on(AgentAction.addAgent, (state, action) => ({
        ...state,
        isLoading: true,
        newProfile: action.profileDetails
    })),
    on(AgentAction.addAgentSuccess, (state, action) => ({
        ...state,
        isLoading: false,
        agentBasics: [...state.agentBasics ?? [], action.agentBasics],
        newProfile: action.profileDetails
    })),
    on(AgentAction.addAgentFailure, (state, action) => ({
        ...state,
        isLoading: false,
        error: action.error 
    })),
)