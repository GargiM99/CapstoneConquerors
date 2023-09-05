import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViewAgentComponent } from './view-agent.component';
import { AgentCardComponent } from 'src/app/share/ui/cards/agent-card/agent-card.component';
import { AgentUtilityBarComponent } from '../../ui/agent-utility-bar/agent-utility-bar.component';
import { AgentFilterPipe } from '../../data-access/pipe/agent-filter.pipe';

@NgModule({
  declarations: [ViewAgentComponent],
  imports: [
    CommonModule,
    AgentCardComponent,
    AgentUtilityBarComponent,
    AgentFilterPipe
  ],
  exports: [ViewAgentComponent]
})
export class ViewAgentModule { }
