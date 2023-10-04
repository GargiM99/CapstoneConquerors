import { Component, ElementRef, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IClientCompForm } from '../../data-access/types/client-modal-input.interface';
import { IModal } from 'src/app/share/data-access/types/modal/modal.interface';

@Component({
  selector: 'app-create-complete-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './create-complete-modal.component.html',
  styleUrls: ['./create-complete-modal.component.scss']
})
export class CreateCompleteModalComponent implements IModal{
  @Output() closeEvent = new EventEmitter()
  @Input() inputValue!: IClientCompForm;

  close() {
    this.elementRef.nativeElement.remove()
  }

  constructor(private elementRef: ElementRef){}
}
