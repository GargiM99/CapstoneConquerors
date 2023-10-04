import { IAgentDetailState } from "src/app/agent/data-access/types/agent-details-state.interface";
import { IAgentState } from "../../../agent/data-access/types/agent-basics-state.interface";
import { ITokenDetailState } from "./auth/token-details-state.interface";
import { IProfileDetailState } from "./profile/profile-details-state.interface";
import { IClientState } from "src/app/client/data-access/types/client-state.interface";

export interface IAppState{
    tokenDetails: ITokenDetailState,
    agentBasics: IAgentState,
    agentDetails: IAgentDetailState,
    profileDetails: IProfileDetailState,
    clientDetails: IClientState
}