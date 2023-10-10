import { Component, ElementRef, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IModal } from 'src/app/share/data-access/types/modal/modal.interface';
import { Observable } from 'rxjs';
import { IAgentCompForm } from '../../data-access/types/agent-modal-input.interface';

@Component({
  selector: 'app-add-complete-modal',
  standalone: true,
  imports: [CommonModule], 
  templateUrl: './add-complete-modal.component.html',
  styleUrls: ['./add-complete-modal.component.scss']
})
export class AddCompleteModalComponent implements IModal{
  @Output() closeEvent = new EventEmitter()
  @Input() inputValue!: IAgentCompForm;
  
  close() {
    this.elementRef.nativeElement.remove()
  }

  constructor(private elementRef: ElementRef){}
} 
