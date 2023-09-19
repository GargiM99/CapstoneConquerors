import { createReducer, on } from '@ngrx/store'
import { IAgentDetailState } from '../../types/agent-details-state.interface'
import * as AgentDetailsAction from './agent-details-action'

export const intialState: IAgentDetailState = {
    isLoading: false,
    agentDetails: {},
    agentId: null,
    updateDetails: null,
    updatedPassword: "",
    error: null
}

export const agentDetailReducer = createReducer(
  intialState,
  on(AgentDetailsAction.getAgentDetails, (state, action) => ({
    ...state,
    isLoading: true,
    agentId: action.agentId   
  })),
  on(AgentDetailsAction.getAgentDetailsSuccess, (state, action) => {
      const updatedAgentDetails = { ...state.agentDetails };
    
      if (state.agentId !== null) 
        updatedAgentDetails[state.agentId] = action.agentDetails 

      return {
        ...state,
        isLoading: false,
        agentDetails: updatedAgentDetails,
      };
  }),
  on(AgentDetailsAction.getAgentDetailsFailure, (state, action) => ({
      ...state,
      isLoading: false,
      error: action.error
  })),

  on(AgentDetailsAction.updateAgentDetails, (state, action) => ({
    ...state,
    isLoading: true,
    agentId: action.agentId,
    updateDetails: action.agentDetails
  })),
  on(AgentDetailsAction.updateAgentDetailsSuccess, (state, action) => {
    const updatedAgentDetails = { ...state.agentDetails };
    
    if (state.agentId !== null) 
      updatedAgentDetails[state.agentId] = action.agentDetails 
  
    return {
      ...state,
      isLoading: false,
      agentDetails: updatedAgentDetails,
    };
  }),  
  on(AgentDetailsAction.updateAgentDetailsFailure, (state, action) => ({
    ...state,
    isLoading: false,
    error: action.error
  })),

  on(AgentDetailsAction.resetAgentPassword, (state, action) => ({
    ...state,
    isLoading: true,
    agentId: action.agentId   
  })),
  on(AgentDetailsAction.resetAgentPasswordSuccess, (state,action) => ({
    ...state,
    isLoading: false,
    updatedPassword: action.password
  })),
  on(AgentDetailsAction.resetAgentPasswordFailure, (state, action) => ({
    ...state,
    isLoading: false,
    error: action.error
  })),

  on(AgentDetailsAction.promoteAgent, (state, action) => ({
    ...state,
    isLoading: true,
    agentId: action.agentId 
  })),
  on(AgentDetailsAction.promoteAgentSuccess, (state, action) => {
    // Deep copy of agentDetails
    const updatedAgentDetails = JSON.parse(JSON.stringify(state.agentDetails));
  
    if (state.agentId !== null && updatedAgentDetails[state.agentId]) {
      updatedAgentDetails[state.agentId].user.role = "ADMIN";
    }
  
    return {
      ...state,
      isLoading: false,
      agentDetails: updatedAgentDetails,
    };
  }),  
  on(AgentDetailsAction.promoteAgentFailure, (state, action) => ({
    ...state,
    isLoading: false,
    error: action.error
  })),
)