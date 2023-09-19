import { IProfileDetails } from "src/app/share/data-access/types/profile/profile-details.interface"
import { IAgentBasics } from "./agent-basics.interface"

export interface IAddAgentRes{
    username: string,
    password: string,
    id: number
}

export interface IAddAgentAction{
    profile: IProfileDetails,
    agentBasic: IAgentBasics,
}

export interface IResetPasswordRes{
    password: string
}

export interface IPromoteAgentRes{
    isPromoted: boolean
}