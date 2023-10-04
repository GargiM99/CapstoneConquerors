import { HttpErrorResponse } from "@angular/common/http"
import { Observable } from "rxjs"
import { IClientBasics } from "./client-basic.inteface"

export interface IClientCompForm{
    isLoading: Observable<boolean>
    error: Observable<HttpErrorResponse | Error | null>
    clientBasics: Observable<IClientBasics | null>
} 