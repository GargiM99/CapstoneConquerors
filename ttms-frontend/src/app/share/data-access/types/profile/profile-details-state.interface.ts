import { IClientSchedule } from "src/app/client/data-access/types/client/client-schedule.interface"
import { IProfileDetails } from "./profile-details.interface"

export interface IProfileDetailState{
    isLoading: boolean
    profileDetail: IProfileDetails | null
    username: string | null
    profileSchedule: IClientSchedule[]
    profileId: number
    error: string | null
}