import { HttpErrorResponse } from "@angular/common/http"
import { Observable } from "rxjs"
import { IProfileDetails } from "src/app/share/data-access/types/profile/profile-details.interface"
import { IAgentDetails } from "./agent-details.interface"

export interface IAgentCompForm{
    isLoading: Observable<boolean>
    error: Observable<HttpErrorResponse|Error|null>
    newProfile: Observable<IProfileDetails|null>
} 

export interface IResetPasswordModal{
    isLoading: Observable<boolean>
    error: Observable<HttpErrorResponse|Error|null>
    tempPassword: Observable<string>
}