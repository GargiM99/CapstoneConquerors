import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Store, select } from '@ngrx/store';
import { Observable, Subscription } from 'rxjs';
import { isLoadingSelector } from 'src/app/share/data-access/redux/auth/token-selectors';
import { detailSelector, errorSelector } from 'src/app/share/data-access/redux/profile/profile-selectors';
import { maxDateValidator, minDateValidator } from 'src/app/share/data-access/services/validators/dateValidator';
import { IAppState } from 'src/app/share/data-access/types/app-state.interface';
import { IProfileDetails } from 'src/app/share/data-access/types/profile/profile-details.interface';
import * as ProfileAction from '../../../share/data-access/redux/profile/profile-actions'

@Component({
  selector: 'app-profile-details',
  templateUrl: './profile-details.component.html',
  styleUrls: ['./profile-details.component.scss']
})
export class ProfileDetailsComponent implements OnInit, OnDestroy{
  isEditEnable: boolean = false
  MAX_LENGTH = 70;

  profileDetails$: Observable<IProfileDetails | null>
  profileError$: Observable<string | null>
  profileIsLoading$: Observable<boolean>
  profileSub!: Subscription

  isInvalid(groupName: string, fieldName: string): boolean {
    const control = this.agentForm.get(groupName)?.get(fieldName)
    return control ? control.invalid && (control.dirty || control.touched) : true
  }

  enableEdit(){
    this.isEditEnable = true
  }

  editProfile(){
    let updatedProfile = <IProfileDetails>this.agentForm.value
    this.store.dispatch(ProfileAction.updateProfileDetails({ profileDetails: updatedProfile }))
  }

  agentForm = this.fb.group({
    user: this.fb.group({
      username: [''],
      role: ['']
    }),
    person: this.fb.group({
      firstname: ['', [Validators.required, Validators.maxLength(this.MAX_LENGTH)]],
      lastname: ['', [Validators.required, Validators.maxLength(this.MAX_LENGTH)]],
      birthDate: [new Date(), [Validators.required, minDateValidator, maxDateValidator ]]
    }),
    address: this.fb.group({
      addressLine: ['', [Validators.required, Validators.maxLength(this.MAX_LENGTH + 100)]],
      postalCode: ['', [Validators.required, Validators.maxLength(this.MAX_LENGTH)]],
      city: ['', [Validators.required, Validators.maxLength(this.MAX_LENGTH + 100)]],
      province: ['', [Validators.required, Validators.maxLength(this.MAX_LENGTH + 100)]],
      country: ['', [Validators.required, Validators.maxLength(this.MAX_LENGTH)]],
    }),
    contact: this.fb.group({
      email: ['', [Validators.required, Validators.maxLength(this.MAX_LENGTH), Validators.email]],
      primaryPhoneNumber: ['', [Validators.required, Validators.maxLength(this.MAX_LENGTH)]],
      secondaryPhoneNumber: ['', [Validators.maxLength(this.MAX_LENGTH)]]
    }) 
  })
  
  constructor(private store: Store<IAppState>, private fb: FormBuilder){
    this.profileDetails$ = this.store.pipe(select(detailSelector))
    this.profileIsLoading$ = this.store.pipe(select(isLoadingSelector))
    this.profileError$ = this.store.pipe(select(errorSelector))
  }
  ngOnDestroy(): void {
    this.profileSub.unsubscribe()
  }

  ngOnInit(): void {


    this.profileSub = this.profileDetails$.subscribe((data) => {
      if (data) {
        this.agentForm.patchValue({
          user:{
            username: data.user ? data.user.username : null,
            role: data.user ? data.user.role : null
          },
          person: {
            firstname: data.person.firstname,
            lastname: data.person.lastname,
            birthDate: data.person.birthDate          
          },
          contact: {
            email: data.contact.email,
            primaryPhoneNumber: data.contact.primaryPhoneNumber,
            secondaryPhoneNumber: data.contact.secondaryPhoneNumber
          },
          address: {
            addressLine: data.address.addressLine,
            city: data.address.city,
            country: data.address.country,
            postalCode: data.address.postalCode,
            province: data.address.province
          }
        })
      }
    })
  }
}
