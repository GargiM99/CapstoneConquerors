import { Component, OnInit } from '@angular/core';
import { Agent } from 'src/app/models/agent';
import { AgentService } from 'src/app/services/agent.service';

@Component({
  selector: 'app-agent-list',
  templateUrl: './agent-list.component.html',
  styleUrls: ['./agent-list.component.scss']
})

export class AgentListComponent implements OnInit{

  agents !: Agent[];

  constructor (private agentService : AgentService){}

  ngOnInit(): void {
   this.agentService.findAll().subscribe(data =>{
    this.agents = data;
   })
  }

}
