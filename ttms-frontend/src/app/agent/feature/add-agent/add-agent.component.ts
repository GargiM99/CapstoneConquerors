import { Component, ViewContainerRef } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { IAppState } from 'src/app/share/data-access/types/app-state.interface';
import { Store, select } from '@ngrx/store';
import { agentErrorSelector, agentIsLoadingSelector, agentProfileSelector } from '../../data-access/redux/agent-selectors';
import { FormBuilder, Validators } from '@angular/forms';
import { maxDateValidator, minDateValidator } from 'src/app/share/data-access/services/validators/dateValidator';
import * as AgentAction from '../../data-access/redux/agent-actions'
import { IProfileDetails, User } from 'src/app/share/data-access/types/profile/profile-details.interface';
import { ModalService } from 'src/app/share/data-access/services/modal/modal.service';
import { AddCompleteModalComponent } from '../../ui/add-complete-modal/add-complete-modal.component';
import { IAgentCompForm } from '../../data-access/types/agent-modal-input.interface';

@Component({
  selector: 'app-add-agent',
  templateUrl: './add-agent.component.html',
  styleUrls: ['./add-agent.component.scss']
})
export class AddAgentComponent{
  MAX_LENGTH = 70;

  agentIsLoading$: Observable<boolean>
  agentError$: Observable<HttpErrorResponse | Error | null>
  newAgentProfile$: Observable<IProfileDetails | null>

  agentForm = this.fb.group({
    person: this.fb.group({
      firstname: ['', [Validators.required, Validators.maxLength(this.MAX_LENGTH)]],
      lastname: ['', [Validators.required, Validators.maxLength(this.MAX_LENGTH)]]
    }),
    contact: this.fb.group({
      email: ['', [Validators.required, Validators.maxLength(this.MAX_LENGTH), Validators.email]],
      primaryPhoneNumber: ['', [Validators.required, Validators.maxLength(this.MAX_LENGTH)]],
      secondaryPhoneNumber: ['', [Validators.maxLength(this.MAX_LENGTH)]]
    }) 
  })

  onAdd(){
    let agentComplete$ : IAgentCompForm = {
      error: this.agentError$, 
      newProfile: this.newAgentProfile$,
      isLoading: this.agentIsLoading$ 
    } 

    let newAgent = <IProfileDetails>this.agentForm.value
    this.store.dispatch(AgentAction.addAgent({ profileDetails: newAgent })) 
    this.modalService.open(AddCompleteModalComponent, this.viewContainerRef, true, agentComplete$)
  } 

  isInvalid(groupName: string, fieldName: string): boolean {
    const control = this.agentForm.get(groupName)?.get(fieldName)
    return control ? control.invalid && (control.dirty || control.touched) : true
  }

  constructor(private store: Store<IAppState>, private fb: FormBuilder,
      private modalService: ModalService, private viewContainerRef: ViewContainerRef){
    
    this.agentIsLoading$ = this.store.pipe(select(agentIsLoadingSelector))
    this.agentError$ = this.store.pipe(select(agentErrorSelector))
    this.newAgentProfile$ = this.store.pipe(select(agentProfileSelector))
  }

}


