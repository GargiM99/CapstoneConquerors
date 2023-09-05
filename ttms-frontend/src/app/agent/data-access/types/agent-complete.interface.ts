import { HttpErrorResponse } from "@angular/common/http"
import { Observable } from "rxjs"
import { IProfileDetails } from "src/app/share/data-access/types/profile/profile-details.interface"

export interface IAgentCompForm{
    isLoading: Observable<boolean>
    error: Observable<HttpErrorResponse|Error|null>
    newProfile: Observable<IProfileDetails|null>
} 