import { Injectable } from '@angular/core';
import * as ProfileAction from './profile-actions'
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { ProfileService } from '../../services/profile/profile.service';
import { catchError, map, mergeMap, of } from 'rxjs';
import { IAppState } from '../../types/app-state.interface';
import { Store } from '@ngrx/store';

@Injectable()
export class ProfileEffect{
    getProfile$ = createEffect(() =>
        this.actions$.pipe(
            ofType(ProfileAction.getProfileDetails),
            mergeMap((action) => 
                this.profileService.getProfile(action.username).pipe(
                    map((profileDetails) =>{ 
                        setTimeout(() => {
                            console.log(profileDetails.user?.id)
                            this.store.dispatch(ProfileAction.
                                getProfileSchedule({ 'id': profileDetails.user?.id ?? -1 }))
                        }, 500)
                        return ProfileAction.getProfileDetailsSuccess({ profileDetails })
                    }),
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

    getSchedule$ = createEffect(() =>
        this.actions$.pipe(
            ofType(ProfileAction.getProfileSchedule),
            mergeMap((action) => 
                this.profileService.getSchedule(action.id).pipe(
                    map((profileSchedule) => ProfileAction.getProfileScheduleSuccess({ profileSchedule })),
                    catchError((error) => 
                        of(ProfileAction.getProfileScheduleFailure({ error: error.message }))
                    ) 
                )
            ),
            catchError((error) => 
                of(ProfileAction.getProfileScheduleFailure({ error:error.message }))
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

    updatePassword$= createEffect(() =>
        this.actions$.pipe(
            ofType(ProfileAction.updatePassword),
            mergeMap((action) =>
                this.profileService.updatePassword(action.authDetails).pipe(
                    map((authDetails) => ProfileAction.updatePasswordSuccess({ authDetails })),
                    catchError((error) =>
                        of(ProfileAction.updatePasswordFailure({ error: error.message }))
                    )
                )
            ),
            catchError((error) =>
                of(ProfileAction.updatePasswordFailure({ error: error.message }))
            )
        )
    )

    constructor(
        private actions$: Actions,
        private profileService: ProfileService,
        private store: Store<IAppState>
    ) {}
}