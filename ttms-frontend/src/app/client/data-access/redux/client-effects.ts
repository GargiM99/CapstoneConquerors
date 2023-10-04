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

    constructor( private actions$: Actions, private clientService: ClientService) {}
}