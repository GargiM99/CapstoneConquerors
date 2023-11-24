import { HttpErrorResponse } from "@angular/common/http"
import { IClientBasics } from "./client-basic.inteface"
import { IClientDetails } from "./client-details.interface"
import { IClientNotes } from "./client-note.interface"

export interface IClientState{
    isLoading: boolean
    clientDetails: IClientDetails | null
    clientBasics: IClientBasics[] 
    clientId: number | null
    modifyClientNotes: IClientNotes[]
    error: HttpErrorResponse | Error | null
}