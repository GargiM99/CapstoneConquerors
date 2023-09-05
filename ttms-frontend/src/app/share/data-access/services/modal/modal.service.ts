import { ComponentRef, Injectable, Type, ViewContainerRef } from '@angular/core';
import { Subject } from 'rxjs';
import { IModal, ModalAction } from '../../types/modal/modal.interface';

@Injectable({
  providedIn: 'root'
})
export class ModalService {
  private modalNotifier?: Subject<ModalAction> 
  constructor() { }

  open(component: Type<IModal>, viewContainerRef: ViewContainerRef, focus: boolean = true, inputValue?: any ){
    this.modalNotifier = new Subject()
    const modalComponent = viewContainerRef.createComponent<IModal>(component)

    if(inputValue !== undefined)
      modalComponent.instance.inputValue = inputValue;

    if (focus)
      this.createBackdrop(modalComponent)

    modalComponent.instance.closeEvent.subscribe((val?) => 
      this.closeModal(<ModalAction>{action: "CLOSE", payload: val}))

    modalComponent.instance.submitEvent?.subscribe((val?) => 
      this.closeModal(<ModalAction>{action: "SUBMIT", payload: val}))

    return this.modalNotifier.asObservable()
  }

  closeModal(modalAction : ModalAction){
    this.modalNotifier?.next(modalAction)
    this.modalNotifier?.complete()
  }

  createBackdrop(modalComponent : ComponentRef<IModal>){
    const backdropDiv = document.createElement('div')
    backdropDiv.classList.add('modal-backdrop')
    backdropDiv.addEventListener('click', () => modalComponent.instance.close());
  
    const modalElement = modalComponent.location.nativeElement as HTMLElement;
    modalElement.appendChild(backdropDiv);
  }
}
