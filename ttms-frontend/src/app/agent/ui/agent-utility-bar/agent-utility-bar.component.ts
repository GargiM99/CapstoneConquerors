import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TAgentSearch } from '../../data-access/types/agent-search.type';
import { Router } from '@angular/router';

@Component({
  selector: 'agent-utility-bar',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './agent-utility-bar.component.html',
  styleUrls: ['./agent-utility-bar.component.scss']
})
export class AgentUtilityBarComponent {
  @Input() canAdd: boolean = false

  @Output() searchValueChange = new EventEmitter<string>()
  @Output() fieldChoiceChange = new EventEmitter<TAgentSearch>()
  searchValue = ''
  fieldChoice: TAgentSearch = 'firstname'

  onSearchChange(): void{
    this.searchValueChange.emit(this.searchValue ?? '')
  }

  onFieldChange(): void{
    this.fieldChoiceChange.emit(this.fieldChoice ?? 'firstname')
  }

  goToAdd(){
    this.router.navigate(['/agent/add'])
  }

  constructor(private router: Router){}
}