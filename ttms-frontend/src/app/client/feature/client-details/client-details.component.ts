import { Component, OnDestroy, OnInit, ViewContainerRef } from '@angular/core';
import { Observable, Subscription, map } from 'rxjs';
import { IClientDetails } from '../../data-access/types/client/client-details.interface';
import { HttpErrorResponse } from '@angular/common/http';
import { Store, select } from '@ngrx/store';
import { IAppState } from 'src/app/share/data-access/types/app-state.interface';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { clientDetailsSelector, clientErrorSelector, clientIsLoadingSelector } from '../../data-access/redux/client/client-selectors';
import { maxDateValidator, minDateValidator } from 'src/app/share/data-access/services/validators/dateValidator';
import * as ClientAction from '../../data-access/redux/client/client-actions'
import * as TripAction from '../../data-access/redux/trip/trip-action'
import { ITripDetails } from '../../data-access/types/trip/trip-details.interface';
import { ModalService } from 'src/app/share/data-access/services/modal/modal.service';
import { TripCreateModalComponent } from '../../ui/modals/trip-create-modal/trip-create-modal.component';
import { tripTypeSelector } from '../../data-access/redux/trip/trip-selectors';
import { ITripType } from '../../data-access/types/trip/trip-type.interface';
import { ITripCreateModalDetails } from '../../data-access/types/trip/create-trip-details.interface';

@Component({
  selector: 'client-details',
  templateUrl: './client-details.component.html',
  styleUrls: ['./client-details.component.scss']
})
export class ClientDetailsComponent implements OnInit, OnDestroy{
  isEditEnable: boolean = false
  MAX_LENGTH = 70;

  clientId: number
  clientDetails$: Observable<IClientDetails | null>
  tripDetails$: Observable<ITripDetails[] | undefined>
  clientError$: Observable<Error | HttpErrorResponse | null>
  clientIsLoading$: Observable<boolean>
  clientSub!: Subscription

  tripType$: Observable<ITripType[]>

  clientForm = this.fb.group({
    user: this.fb.group({ 
      username: ['']
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

  isInvalid(groupName: string, fieldName: string): boolean {
    const control = this.clientForm.get(groupName)?.get(fieldName)
    return control ? control.invalid && (control.dirty || control.touched) : true
  } 

  editDetails(){
    let updatedClient = <IClientDetails>this.clientForm.value
    this.store.dispatch(ClientAction.updateClient({ clientId: this.clientId, clientDetails: updatedClient }))
  }

  toggleEdit(){
    this.isEditEnable = !this.isEditEnable
  }

  createTripModal(){
    const modalInput: ITripCreateModalDetails = {clientId: this.clientId, tripType$: this.tripType$}
    this.modalService.open(TripCreateModalComponent, this.viewContainerRef, true, modalInput)
  }

  ngOnInit(): void {
    this.store.dispatch(ClientAction.getClientDetails({ clientId: this.clientId }))
    this.store.dispatch(TripAction.getTripType())

    this.clientSub = this.clientDetails$.subscribe((data) => {
      if (data) {
        this.clientForm.patchValue({
          user:{
            username: data.user.username,
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

  ngOnDestroy(): void { this.clientSub.unsubscribe() }

  constructor(private store: Store<IAppState>, private route: ActivatedRoute,
    private fb: FormBuilder, private viewContainerRef: ViewContainerRef, private modalService: ModalService){
      
    this.clientId = +(this.route.snapshot.paramMap.get('id') ?? 0)

    this.clientIsLoading$ = this.store.pipe(select(clientIsLoadingSelector))
    this.clientError$ = this.store.pipe(select(clientErrorSelector))
    this.clientDetails$ = this.store.pipe(select(clientDetailsSelector))

    this.tripDetails$ = this.clientDetails$.pipe(map((client) => client?.tripDetails));
    this.tripType$ = this.store.pipe(select(tripTypeSelector))
  }
}
