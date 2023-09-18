import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IAgentBasics } from 'src/app/agent/data-access/types/agent-basics.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'agent-card',
  standalone: true, 
  imports: [CommonModule],
  templateUrl: './agent-card.component.html',
  styleUrls: ['./agent-card.component.scss']
})
export class AgentCardComponent {
  @Input() agent!: IAgentBasics

  viewProfile(id: number){
    this.router.navigate([`agent`, id])
  }
  
  constructor(private router: Router){}
}
