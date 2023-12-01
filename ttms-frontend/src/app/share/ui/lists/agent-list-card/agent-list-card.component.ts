import { AfterViewChecked, Component, HostListener, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IAgentBasics } from 'src/app/agent/data-access/types/agent-basics.interface';
import { Observable, of } from 'rxjs';
import { AgentCardComponent } from '../../cards/agent-card/agent-card.component';
import { ArrowButtonComponent } from '../../arrow-button/arrow-button.component';
import { Router } from '@angular/router';
import { TokenDetailsService } from 'src/app/share/data-access/services/auth/token-details.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'agent-list-card',
  standalone: true,
  imports: [CommonModule, AgentCardComponent, ArrowButtonComponent],
  templateUrl: './agent-list-card.component.html',
  styleUrls: ['./agent-list-card.component.scss']
})
export class AgentListCardComponent implements OnInit {
  @Input() agents$: Observable<IAgentBasics[] | null> = of([])
  @Input() error$?: Observable<HttpErrorResponse | Error | null> 

  MAX_NUMBER_CARDS = 5
  CARD_SIZE = 300
  numberCards = this.MAX_NUMBER_CARDS
  role = this.tokenService.getRole()

  ngOnInit(): void {
    this.calculateCardNum()
  }
 
  viewAll(){ this.router.navigate(['agent']) }

  goAddAgent(){ this.router.navigate(['agent/add']) }

  @HostListener('window:resize', ['$event'])
  onResize(event: any): void { this.numberCards = this.calculateCardNum() }

  calculateCardNum(){
    let screenWidth: number = window.innerWidth;
    const cardWidth = this.CARD_SIZE
    this.numberCards = Math.max(Math.min(Math.round(screenWidth/cardWidth) - 1, this.MAX_NUMBER_CARDS), 1)
    return this.numberCards
  }

  constructor(private router: Router, private tokenService: TokenDetailsService){}
}
