import { IClientSchedule } from "src/app/share/data-access/types/calendar/client-schedule.interface"
import { IProfileDetails } from "./profile-details.interface"
import { IProfileAuthDetails } from "./profile-auth-details.interface"

export interface IProfileDetailState{
    isLoading: boolean
    profileDetail: IProfileDetails | null
    username: string | null
    profileSchedule: IClientSchedule[]
    profileId: number
    profileAuthDetails: IProfileAuthDetails | null
    error: string | null
}