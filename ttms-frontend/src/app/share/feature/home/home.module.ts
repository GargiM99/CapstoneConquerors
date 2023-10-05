import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';
import { ProfileService } from '../../data-access/services/profile/profile.service';
import { AgentListCardComponent } from '../../ui/lists/agent-list-card/agent-list-card.component';
import { ClientListCardComponent } from '../../ui/lists/client-list-card/client-list-card.component';

@NgModule({
  declarations: [HomeComponent],
  imports: [CommonModule, AgentListCardComponent, ClientListCardComponent],
  providers: [ProfileService],
  exports: [HomeComponent]
})
export class HomeModule { }
