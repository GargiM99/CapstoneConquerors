import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TtmsHeaderComponent } from './ttms-header/ttms-header.component';
import { NgbDropdownModule, NgbCollapseModule, NgbNavModule  } from '@ng-bootstrap/ng-bootstrap';

/*
* author: Hamza 
* date: 01/03/2023
* description: Holds all the common components like the header and footer
*/

@NgModule({
  declarations: [
    TtmsHeaderComponent
  ],
  imports: [
    CommonModule,
    NgbDropdownModule,
    NgbCollapseModule,
    NgbNavModule
  ],
  exports: [
    TtmsHeaderComponent
  ]
})
export class TtmsCommonModule { }
