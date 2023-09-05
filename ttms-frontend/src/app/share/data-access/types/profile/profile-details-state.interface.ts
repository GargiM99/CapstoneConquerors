import { IProfileDetails } from "./profile-details.interface"

export interface IProfileDetailState{
    isLoading: boolean
    profileDetail: IProfileDetails | null
    username: string | null
    error: string | null
}