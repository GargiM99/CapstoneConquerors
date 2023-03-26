import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AgentService } from 'src/app/auth/agent.service';
import { BasicAgentDetails } from 'src/app/classes/basic-agent-details';

@Component({
  selector: 'app-ttms-agent-list',
  templateUrl: './ttms-agent-list.component.html',
  styleUrls: ['./ttms-agent-list.component.scss']
})
export class TtmsAgentListComponent implements OnInit{

  agents : BasicAgentDetails[] = new Array()

  constructor(private agentService : AgentService, private router : Router){}

  ngOnInit(): void {
    let responsePromise = this.agentService.getAgents()

    responsePromise.then(
      (response) =>{
        if (response instanceof HttpErrorResponse){
          this.router.navigate(['/'])
        }
        else{
          this.agents = response
        }
      }
    )
  }

}
