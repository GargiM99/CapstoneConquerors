import { Component, ElementRef, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IModal } from 'src/app/share/data-access/types/modal/modal.interface';
import { IProfileAuthDetails } from 'src/app/share/data-access/types/profile/profile-auth-details.interface';
import { Observable } from 'rxjs';
import { Store, select } from '@ngrx/store';
import { IAppState } from 'src/app/share/data-access/types/app-state.interface';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { errorSelector } from 'src/app/share/data-access/redux/profile/profile-selectors';
import { regexValidator } from 'src/app/share/data-access/services/validators/regexValidator';
import * as ProfileAction from '../../../share/data-access/redux/profile/profile-actions'

@Component({
  selector: 'update-password-modal',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './update-password-modal.component.html',
  styleUrls: ['./update-password-modal.component.scss']
})
export class UpdatePasswordModalComponent implements IModal, OnInit{
  @Output() closeEvent = new EventEmitter()
  @Input() inputValue!: IProfileAuthDetails

  MAX_LENGTH = 70;
  PASSWORD_REGEX = /^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%^&*-]).{8,}$/
  profileError$: Observable<string | null>
  isSubmitted: boolean = false

  passwordForm = this.fb.group({
    id: [0],
    username: [''],
    password: ['', [Validators.required, Validators.maxLength(this.MAX_LENGTH), regexValidator(this.PASSWORD_REGEX)]],  
  })

  onUpdate(){
    let newAuthDetails = <IProfileAuthDetails>this.passwordForm.value
    this.store.dispatch(ProfileAction.updatePassword ({ authDetails: newAuthDetails }))
    this.isSubmitted = true
  }

  isInvalid(fieldName: string): boolean {
    const control = this.passwordForm?.get(fieldName)
    return control ? control.invalid && (control.dirty || control.touched) : true
  }

  close() {
    this.elementRef.nativeElement.remove()
  }

  submit() {
    this.elementRef.nativeElement.remove()
  }

  ngOnInit(): void {
    this.passwordForm.patchValue({
      id: this.inputValue.id,
      username: this.inputValue.username
    })
  }

  constructor(private store: Store<IAppState>, private elementRef: ElementRef, private fb: FormBuilder){   
    this.profileError$ = this.store.pipe(select(errorSelector))
  }
}
