import { AfterViewChecked, Component, HostListener, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IAgentBasics } from 'src/app/agent/data-access/types/agent-basics.interface';
import { Observable, of } from 'rxjs';
import { AgentCardComponent } from '../../cards/agent-card/agent-card.component';
import { ArrowButtonComponent } from '../../arrow-button/arrow-button.component';
import { Router } from '@angular/router';

@Component({
  selector: 'agent-list-card',
  standalone: true,
  imports: [CommonModule, AgentCardComponent, ArrowButtonComponent],
  templateUrl: './agent-list-card.component.html',
  styleUrls: ['./agent-list-card.component.scss']
})
export class AgentListCardComponent implements AfterViewChecked  {
  @Input() agents$: Observable<IAgentBasics[] | null> = of([])
  MAX_NUMBER_CARDS = 5
  numberCards = this.MAX_NUMBER_CARDS

  ngAfterViewChecked(): void {
    this.calculateCardNum()
  }

  viewAll(){ this.router.navigate(['agent']) }

  @HostListener('window:resize', ['$event'])
  onResize(event: any): void { this.numberCards = this.calculateCardNum() }

  calculateCardNum(){
    let screenWidth: number = window.innerWidth;
    const agentCard = document.getElementById('agent-card');

    if (agentCard) {
      const cardWidth = agentCard.offsetWidth;
      this.numberCards = Math.max(Math.min(Math.round(screenWidth/cardWidth) - 1, this.MAX_NUMBER_CARDS), 1)
    }

    return this.numberCards
  }

  constructor(private router: Router){
  }
}
