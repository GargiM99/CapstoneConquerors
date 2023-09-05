import { Injectable } from '@angular/core';
import * as ProfileAction from './profile-actions'
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { ProfileService } from '../../services/profile/profile.service';
import { catchError, map, mergeMap, of } from 'rxjs';

@Injectable()
export class ProfileEffect{
    getProfile$ = createEffect(() =>
        this.actions$.pipe(
            ofType(ProfileAction.getProfileDetails),
            mergeMap((action) => 
                this.profileService.getProfile(action.username).pipe(
                    map((profileDetails) => ProfileAction.getProfileDetailsSuccess({ profileDetails })),
                    catchError((error) => 
                        of(ProfileAction.getProfileDetailsFailure({ error: error.message }))
                    ) 
                )
            ),
            catchError((error) => 
                of(ProfileAction.getProfileDetailsFailure({ error:error.message }))
            )
        )
    )

    updateProfile$= createEffect(() =>
        this.actions$.pipe(
            ofType(ProfileAction.updateProfileDetails),
            mergeMap((action) =>
                this.profileService.updateProfile(action.profileDetails).pipe(
                    map((profileDetails) => ProfileAction.updateProfileDetailsSuccess({ profileDetails })),
                    catchError((error) =>
                        of(ProfileAction.getProfileDetailsFailure({ error: error.message }))
                    )
                )
            ),
            catchError((error) =>
                of(ProfileAction.getProfileDetailsFailure({ error: error.message }))
            )
        )
    )

    constructor(
        private actions$: Actions,
        private profileService: ProfileService
    ) {}
}