import { createAction, props } from '@ngrx/store';
import { IProfileDetails } from '../../types/profile/profile-details.interface';
import { IClientSchedule } from 'src/app/share/data-access/types/calendar/client-schedule.interface';

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

export const getProfileSchedule = createAction(
  '[ProfileSchedule] Get Profile Schedule',
  props<{ id: number }>()
)
export const getProfileScheduleSuccess = createAction(
  '[ProfileSchedule] Get Profile Schedule Success',
  props<{ profileSchedule: IClientSchedule[] }>()
)
export const getProfileScheduleFailure = createAction(
  '[ProfileSchedule] Get Profile Schedule Failure',
  props<{ error: string | null }>()
)