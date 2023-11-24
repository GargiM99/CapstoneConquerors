import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AgentScheduleComponent } from './agent-schedule/agent-schedule.component';
import { FullCalendarModule } from '@fullcalendar/angular';
import { EventTooltipComponent } from './event-tooltip/event-tooltip.component';

@NgModule({
  declarations: [AgentScheduleComponent, EventTooltipComponent],
  imports: [CommonModule, FullCalendarModule],
  exports: [AgentScheduleComponent]
})
export class SchedulesModule { }
