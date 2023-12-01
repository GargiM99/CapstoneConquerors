import { Component, HostListener, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientCardComponent } from '../../cards/client-card/client-card.component';
import { ArrowButtonComponent } from '../../arrow-button/arrow-button.component';
import { Observable, of } from 'rxjs';
import { IClientBasics } from 'src/app/client/data-access/types/client/client-basic.inteface';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'client-list-card',
  standalone: true,
  imports: [CommonModule, ClientCardComponent, ArrowButtonComponent],
  templateUrl: './client-list-card.component.html',
  styleUrls: ['./client-list-card.component.scss']
})
export class ClientListCardComponent implements OnInit {
  @Input() clients$: Observable<IClientBasics[] | null> = of([])
  @Input() error$?: Observable<HttpErrorResponse | Error | null> 
  
  MAX_NUMBER_CARDS = 5
  CARD_SIZE = 300
  numberCards = this.MAX_NUMBER_CARDS

  ngOnInit(): void {
    this.calculateCardNum()
  }

  viewAll(){ this.router.navigate(['client']) }
  goCreateClient(){ this.router.navigate(['client/create']) }

  @HostListener('window:resize', ['$event'])
  onResize(event: any): void { this.numberCards = this.calculateCardNum() }

  calculateCardNum(){
    let screenWidth: number = window.innerWidth;
    const cardWidth = this.CARD_SIZE
    this.numberCards = Math.max(Math.min(Math.round(screenWidth/cardWidth) - 1, this.MAX_NUMBER_CARDS), 1)
    return this.numberCards 
  }

  constructor(private router: Router){}
}
