import { HttpErrorResponse } from "@angular/common/http"
import { IClientBasics } from "./client-basic.inteface"
import { IClientDetails } from "./client-details.interface"

export interface IClientState{
    isLoading: boolean
    clientDetails: IClientDetails | null
    clientBasics: IClientBasics[] 
    clientId: number | null
    error: HttpErrorResponse | Error | null
}