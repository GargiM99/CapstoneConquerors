import { createAction, props } from "@ngrx/store";
import { IClientBasics } from "../types/client-basic.inteface";
import { HttpErrorResponse } from "@angular/common/http";
import { IClientDetails } from "../types/client-details.interface";

export const getClientBasics = createAction(
    '[Client] Get Client Basics',
    props<{ clientId: number | null }>()
)
export const getClientBasicsSuccess = createAction(
    '[Client] Get Client Basics Success',
    props<{ clientBasics: IClientBasics[] | null }>()
)
export const getClientBasicsFailure = createAction(
    '[Client] Get Client Basics Failure',
    props<{ error: HttpErrorResponse | Error | null }>()
)

export const createClient = createAction(
    '[Client] Create Client',
    props<{ clientDetails: IClientDetails }>()
)
export const createClientSuccess = createAction(
    '[Client] Create Client Success',
    props<{ clientBasics: IClientBasics }>()
)
export const createClientFailure = createAction(
    '[Client] Create Client Failure',
    props<{ error: HttpErrorResponse | Error | null }>()
)

