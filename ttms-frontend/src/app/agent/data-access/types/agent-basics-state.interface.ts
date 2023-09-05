import { HttpErrorResponse } from "@angular/common/http"
import { IAgentBasics } from "./agent-basics.interface"
import { IProfileDetails } from "src/app/share/data-access/types/profile/profile-details.interface"

export interface IAgentState{
    isLoading: boolean
    newProfile: IProfileDetails | null
    agentBasics: IAgentBasics[] 
    agentIds: number[] | null
    error: HttpErrorResponse | Error | null
}