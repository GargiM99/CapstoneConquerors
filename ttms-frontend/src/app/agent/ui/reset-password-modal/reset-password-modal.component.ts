import { Component, ElementRef, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IAgentCompForm, IResetPasswordModal } from '../../data-access/types/agent-modal-input.interface';

@Component({
  selector: 'app-reset-password-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './reset-password-modal.component.html',
  styleUrls: ['./reset-password-modal.component.scss']
})
export class ResetPasswordModalComponent {
  @Output() closeEvent = new EventEmitter()
  @Input() inputValue!: IResetPasswordModal;

  close() {
    this.elementRef.nativeElement.remove()
  }

  constructor(private elementRef: ElementRef){}
}
