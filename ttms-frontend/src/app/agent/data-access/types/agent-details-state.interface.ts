import { HttpErrorResponse } from "@angular/common/http"
import { IAgentDetails } from "./agent-details.interface"

export interface IAgentDetailState{
    isLoading: boolean
    agentDetails: { [agentId: number]: IAgentDetails } 
    agentId: number | null
    updateDetails: IAgentDetails | null
    updatedPassword: string 
    error: HttpErrorResponse | Error | null
}