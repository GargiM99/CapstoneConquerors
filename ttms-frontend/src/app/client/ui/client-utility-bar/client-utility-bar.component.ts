import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { TClientSearch } from '../../data-access/types/client/client-search.type';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'client-utility-bar',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './client-utility-bar.component.html',
  styleUrls: ['./client-utility-bar.component.scss']
})
export class ClientUtilityBarComponent {
  @Output() searchValueChange = new EventEmitter<string>()
  @Output() fieldChoiceChange = new EventEmitter<TClientSearch>()
  searchValue = ''
  fieldChoice: TClientSearch = 'firstname'

  onSearchChange(): void{
    this.searchValueChange.emit(this.searchValue ?? '')
  }

  onFieldChange(): void{
    this.fieldChoiceChange.emit(this.fieldChoice ?? 'firstname')
  }

  goToAdd(){
    this.router.navigate(['/client/create'])
  }

  constructor(private router: Router){}
}
