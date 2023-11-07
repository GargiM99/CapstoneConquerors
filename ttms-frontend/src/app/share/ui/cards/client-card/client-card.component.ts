import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IClientBasics } from 'src/app/client/data-access/types/client/client-basic.inteface';
import { Router } from '@angular/router';

@Component({
  selector: 'client-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './client-card.component.html',
  styleUrls: ['./client-card.component.scss']
})
export class ClientCardComponent {
  @Input() client!: IClientBasics 

  viewDetails(id: number){
    this.router.navigate([`client`, id])
  }
  
  constructor(private router: Router){}
}
