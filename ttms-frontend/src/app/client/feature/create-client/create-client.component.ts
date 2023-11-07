import { HttpErrorResponse } from '@angular/common/http';
import { Component, ViewContainerRef } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Store, select } from '@ngrx/store';
import { Observable, map, mergeMap } from 'rxjs';
import { IAppState } from 'src/app/share/data-access/types/app-state.interface';
import { IClientDetails } from '../../data-access/types/client/client-details.interface';
import * as ClientAction from '../../data-access/redux/client/client-actions';
import { clientBasicsSelector, clientDetailsSelector, clientErrorSelector, clientIsLoadingSelector } from '../../data-access/redux/client/client-selectors';
import { maxDateValidator, minDateValidator } from 'src/app/share/data-access/services/validators/dateValidator';
import { IClientCompForm } from '../../data-access/types/client/client-modal-input.interface';
import { IClientBasics } from '../../data-access/types/client/client-basic.inteface';
import { ModalService } from 'src/app/share/data-access/services/modal/modal.service';
import { CreateCompleteModalComponent } from '../../ui/modals/create-complete-modal/create-complete-modal.component';

@Component({
  selector: 'app-create-client',
  templateUrl: './create-client.component.html',
  styleUrls: ['./create-client.component.scss']
})
export class CreateClientComponent {
  MAX_LENGTH = 70;

  clientIsLoading$: Observable<boolean>
  clientError$: Observable<HttpErrorResponse | Error | null>
  newClientBasics$: Observable<IClientBasics[] | null>

  clientForm = this.fb.group({
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

  onCreate(){
    let clientBasic = this.newClientBasics$.pipe(
      map((clientBasics) => {
        if (clientBasics != null && clientBasics.length > 0) {
          return clientBasics[clientBasics.length - 1];
        }
        return null;
      })) 
    

    let clientComplete$ : IClientCompForm = {
      error: this.clientError$, 
      clientBasics: clientBasic,
      isLoading: this.clientIsLoading$ 
    } 

    let newClient = <IClientDetails>this.clientForm.value
    this.store.dispatch(ClientAction.createClient({ clientDetails: newClient }))
    this.modalService.open(CreateCompleteModalComponent, this.viewContainerRef, true, clientComplete$)
  }  

  isInvalid(groupName: string, fieldName: string): boolean {
    const control = this.clientForm.get(groupName)?.get(fieldName)
    return control ? control.invalid && (control.dirty || control.touched) : true
  }

  constructor(private store: Store<IAppState>, private fb: FormBuilder,
        private modalService: ModalService, private viewContainerRef: ViewContainerRef){
    this.clientIsLoading$ = this.store.pipe(select(clientIsLoadingSelector))
    this.clientError$ = this.store.pipe(select(clientErrorSelector))
    this.newClientBasics$ = this.store.pipe(select(clientBasicsSelector))
  }
}
