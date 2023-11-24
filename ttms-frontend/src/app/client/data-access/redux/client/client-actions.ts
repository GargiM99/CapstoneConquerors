import { createAction, props } from "@ngrx/store";
import { IClientBasics } from "../../types/client/client-basic.inteface";
import { HttpErrorResponse } from "@angular/common/http";
import { IClientDetails } from "../../types/client/client-details.interface";
import { IClientNotes } from "../../types/client/client-note.interface";

export const getClientBasics = createAction(
    '[Client] Get Client Basics'
)
export const getClientBasicsSuccess = createAction(
    '[Client] Get Client Basics Success', 
    props<{ clientBasics: IClientBasics[] | null }>()
)
export const getClientBasicsFailure = createAction(
    '[Client] Get Client Basics Failure',
    props<{ error: HttpErrorResponse | Error | null }>()
)

export const getClientDetails = createAction(
    '[Client] Get Client Details',
    props<{ clientId: number }>()
)
export const getClientDetailsSuccess = createAction(
    '[Client] Get Client Details Success', 
    props<{ clientDetails: IClientDetails | null }>()
)
export const getClientDetailsFailure = createAction(
    '[Client] Get Client Details Failure',
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

export const updateClient = createAction(
    '[Client] Update Client',
    props<{ clientDetails: IClientDetails, clientId: number }>()
)
export const updateClientSuccess = createAction(
    '[Client] Update Client Success',
    props<{ clientDetails: IClientDetails }>()
)
export const updateClientFailure = createAction(
    '[Client] Update Client Failure',
    props<{ error: HttpErrorResponse | Error | null }>()
)

export const modifyClientNotes = createAction(
    '[Client] Modify Client Notes',
    props<{ clientNotes: IClientNotes[], clientId: number }>()
)
export const modifyClientNotesSuccess = createAction(
    '[Client] Modify Client Notes Success',
    props<{ clientNotes: IClientNotes[] }>()
)
export const modifyClientNotesFailure = createAction(
    '[Client] Modify Client Notes Failure',
    props<{ error: HttpErrorResponse | Error | null }>()
)