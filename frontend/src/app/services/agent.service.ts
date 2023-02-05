import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Agent } from '../models/agent';

@Injectable({
  providedIn: 'root'
})
export class AgentService {

  private agentUrl !: string;

  constructor(private http: HttpClient) { 
    this.agentUrl = 'http://25.68.193.152:8080/agent'
  }

  public findAll() : Observable<Agent[]>{
    return this.http.get<Agent[]>(this.agentUrl);
  }

  public save(agent : Agent){
    return this.http.post<Agent>(this.agentUrl, agent);
  }
}
