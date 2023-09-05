import { HttpErrorResponse } from "@angular/common/http"
import { ILoginDetails } from "./login-details.interface"
import { ITokenDetail } from "./token-details.interface"

export interface ITokenDetailState{
    isLoading: boolean
    tokenDetail: ITokenDetail | null
    loginDetail: ILoginDetails | null
    error: HttpErrorResponse | Error | null
}