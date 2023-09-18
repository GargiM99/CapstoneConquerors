import { Component, OnDestroy, OnInit, ViewContainerRef } from '@angular/core';
import * as AgentDetailAction from '../../data-access/redux/agent-details/agent-details-action'
import { IAgentDetails } from '../../data-access/types/agent-details.interface';
import { IAppState } from 'src/app/share/data-access/types/app-state.interface';
import { Store, select } from '@ngrx/store';
import { agentDetailErrorSelector, agentDetailIsLoadingSelector, agentDetailsSelector, agentUpdatedPasswordSelector } from '../../data-access/redux/agent-details/agent-details-selectors';
import { ActivatedRoute } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { maxDateValidator, minDateValidator } from 'src/app/share/data-access/services/validators/dateValidator';
import { ModalService } from 'src/app/share/data-access/services/modal/modal.service';
import { ResetPasswordModalComponent } from '../../ui/reset-password-modal/reset-password-modal.component';
import { IResetPasswordModal } from '../../data-access/types/agent-modal-input.interface';

@Component({
  selector: 'agent-details',
  templateUrl: './agent-details.component.html',
  styleUrls: ['./agent-details.component.scss']
})
export class AgentDetailsComponent implements OnInit, OnDestroy{
  isEditEnable: boolean = false
  MAX_LENGTH = 70;

  agentId: number
  agentDetails$: Observable<IAgentDetails>
  agentError$: Observable<Error | HttpErrorResponse | null>
  agentIsLoading$: Observable<boolean>
  agentTempPassword$: Observable<string>
  agentSub!: Subscription
  
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

  resetPassword(){
    let resetComplete$ : IResetPasswordModal = {
      error: this.agentError$, 
      tempPassword: this.agentTempPassword$,
      isLoading: this.agentIsLoading$ 
    } 

    this.agentDetails$.forEach((agent) => console.log(agent.user))

    this.store.dispatch(AgentDetailAction.resetAgentPassword({ agentId: this.agentId }))
    this.modalService.open(ResetPasswordModalComponent, this.viewContainerRef, true, resetComplete$)
  }

  enableEdit(){
    this.isEditEnable = true
  }

  editProfile(){
    let updatedAgent = <IAgentDetails>this.agentForm.value
    this.store.dispatch(AgentDetailAction.updateAgentDetails({ agentId: this.agentId, agentDetails: updatedAgent }))
  }

  isInvalid(groupName: string, fieldName: string): boolean {
    const control = this.agentForm.get(groupName)?.get(fieldName)
    return control ? control.invalid && (control.dirty || control.touched) : true
  }

  ngOnInit(): void {
    this.store.dispatch(AgentDetailAction.getAgentDetails({ agentId: this.agentId }))

    this.agentSub = this.agentDetails$.subscribe((data) => {
      if (data) {
        this.agentForm.patchValue({
          user:{
            username: data.user.username,
            role: data.user.role
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

  ngOnDestroy(): void {
    this.agentSub.unsubscribe()
  }

  constructor(private store: Store<IAppState>, private route: ActivatedRoute,
              private fb: FormBuilder, private modalService: ModalService, 
              private viewContainerRef: ViewContainerRef){
                
    this.agentId = +(this.route.snapshot.paramMap.get('id') ?? 0)

    this.agentIsLoading$ = this.store.pipe(select(agentDetailIsLoadingSelector))
    this.agentError$ = this.store.pipe(select(agentDetailErrorSelector))
    this.agentDetails$ = this.store.pipe(select(agentDetailsSelector))
    this.agentTempPassword$ = this.store.pipe(select(agentUpdatedPasswordSelector))
  }
}
