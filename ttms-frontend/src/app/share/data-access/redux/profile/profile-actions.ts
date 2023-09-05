import { createAction, props } from '@ngrx/store';
import { IProfileDetails } from '../../types/profile/profile-details.interface';

export const getProfileDetails = createAction(
  '[ProfileDetail] Get Profile Details',
  props<{ username: string }>()
)
export const getProfileDetailsSuccess = createAction(
  '[ProfileDetail] Get Profile Details Success',
  props<{ profileDetails: IProfileDetails | null }>()
)
export const getProfileDetailsFailure = createAction(
  '[ProfileDetail] Get Profile Details Failure',
  props<{ error: string | null }>()
)

export const updateProfileDetails = createAction(
   '[ProfileDetail] Update Profile Details',
   props<{ profileDetails: IProfileDetails }>()
)
export const updateProfileDetailsSuccess = createAction(
   '[ProfileDetail] Update Profile Details Success',
   props<{ profileDetails: IProfileDetails }>()
)
export const updateProfileDetailsFailure = createAction(
  '[ProfileDetail] Update Profile Details Failure',
  props<{ error: string | null }>()
)

