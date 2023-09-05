import { createAction, props } from "@ngrx/store";
import { ILoginDetails } from "../../types/auth/login-details.interface";
import { ITokenDetail } from "../../types/auth/token-details.interface";
import { HttpErrorResponse } from "@angular/common/http";

export const getTokenDetails = createAction(
    '[TokenDetail] Get Token Details',
    props<{ loginDetails: ILoginDetails | null }>()
)
export const getTokenDetailsSuccess = createAction(
    '[TokenDetail] Get Token Details Success',
    props<{ tokenDetails: ITokenDetail | null }>()    
)
export const getTokenDetailsFailure = createAction(
    '[TokenDetail] Get Token Details Failure',
    props<{ error: Error | HttpErrorResponse | null }>()    
)