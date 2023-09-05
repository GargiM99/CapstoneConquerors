import { Injectable } from '@angular/core';
import * as TokenAction from './token-actions'
import * as ProfileAction from '../profile/profile-actions'
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { TokenDetailsService } from '../../services/auth/token-details.service';
import { catchError, map, mergeMap, of } from 'rxjs';
import { IAppState } from '../../types/app-state.interface';
import { Store } from '@ngrx/store';
 
@Injectable()
export class TokenEffect{
    getToken$ = createEffect(() =>
        this.actions$.pipe(
            ofType(TokenAction.getTokenDetails),
            mergeMap((action) =>
                this.tokenDetailsService.getToken(action.loginDetails).pipe(
                    map((tokenDetails) => {
                        setTimeout(() => {
                            this.store.dispatch(ProfileAction.
                                getProfileDetails({ 'username': tokenDetails.decoded?.sub ?? null }));
                        }, 500)
                        return TokenAction.getTokenDetailsSuccess({ tokenDetails })
                    }),
                    catchError((error) => {
                        return of(TokenAction.getTokenDetailsFailure({ error: error }))
                    })
                )
            ),
            catchError((error) =>
                of(TokenAction.getTokenDetailsFailure({ error: error }))
            )
        )
    )

    constructor(
        private actions$: Actions,
        private tokenDetailsService: TokenDetailsService,
        private store: Store<IAppState>
    ) {}
}