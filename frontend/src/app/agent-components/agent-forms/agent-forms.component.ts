import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Agent } from 'src/app/models/agent';
import { AgentService } from 'src/app/services/agent.service';

@Component({
  selector: 'app-agent-forms',
  templateUrl: './agent-forms.component.html',
  styleUrls: ['./agent-forms.component.scss']
})
export class AgentFormsComponent {
  agent !: Agent;

  constructor(
    private router : Router,
    private route : ActivatedRoute,
    private agentService : AgentService
  ){
    this.agent = new Agent();
  }

  onSubmitAgent(){
    this.agentService.save(this.agent).subscribe(result => this.gotoAgentList());
  }

  gotoAgentList() : void{
    this.router.navigate(['/agent']);
  }
}

