import { Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { ClientService } from "../services/client.service";
import * as ClientAction from "./client-actions";
import { catchError, map, mergeMap, of } from "rxjs";

@Injectable() 
export class ClientEffect{
    createClient$ = createEffect(() =>
        this.actions$.pipe(
            ofType(ClientAction.createClient),
            mergeMap((action) =>
                this.clientService.createClient(action.clientDetails).pipe(
                    map((clientCreateAction) => ClientAction.createClientSuccess({ 
                        clientBasics: clientCreateAction, 
                    })),
                    catchError((error) =>
                        of(ClientAction.createClientFailure({ error: error }))
                    )
                )
            ),
            catchError((error) =>
                of(ClientAction.createClientFailure({ error: error }))
            )
        )
    )

    getClientBasic$ = createEffect(() =>
        this.actions$.pipe(
            ofType(ClientAction.getClientBasics),
            mergeMap((action) =>
                this.clientService.getClientBasics().pipe(
                    map((clientBasics) => ClientAction.getClientBasicsSuccess({clientBasics})),
                    catchError((error) =>
                        of(ClientAction.getClientBasicsFailure({ error: error }))
                    )
                )
            ),
            catchError((error) =>
                of(ClientAction.getClientBasicsFailure({ error: error }))
            )   
        )
    )
    
    getClientDetail$ = createEffect(() =>
        this.actions$.pipe(
            ofType(ClientAction.getClientDetails),
            mergeMap((action) =>
                this.clientService.getClientDetails(action.clientId).pipe(
                    map((clientDetails) => ClientAction.getClientDetailsSuccess({clientDetails})),
                    catchError((error) =>
                        of(ClientAction.getClientDetailsFailure({ error: error }))
                    )
                )
            ),
            catchError((error) =>
                of(ClientAction.getClientDetailsFailure({ error: error }))
            )
        )
    )

    updateClientDetail$ = createEffect(() =>
        this.actions$.pipe(
            ofType(ClientAction.updateClient),
            mergeMap((action) =>
                this.clientService.updateClientDetails(action.clientId, action.clientDetails).pipe(
                    map((clientDetails) => ClientAction.updateClientSuccess({clientDetails})),
                    catchError((error) =>
                        of(ClientAction.updateClientFailure({ error: error }))
                    )
                )
            ),
            catchError((error) =>
                of(ClientAction.updateClientFailure({ error: error }))
            )
        )
    )

    constructor( private actions$: Actions, private clientService: ClientService) {}
}