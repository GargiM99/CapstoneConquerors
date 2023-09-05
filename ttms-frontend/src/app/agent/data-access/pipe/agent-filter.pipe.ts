import { Pipe, PipeTransform } from '@angular/core';
import { IAgentBasics } from '../types/agent-basics.interface';
import { TAgentSearch } from '../types/agent-search.type';

@Pipe({
  name: 'agentFilter',
  standalone: true
})
export class AgentFilterPipe implements PipeTransform {

  transform(agents: IAgentBasics[] | null, searchText: string, field: TAgentSearch): IAgentBasics[] {

    if (!agents) return []
    if (!searchText) return agents
    searchText = searchText.toLowerCase()

    return agents.filter( it =>{
      return it[field].toLowerCase().includes(searchText)
    })
  }

}
