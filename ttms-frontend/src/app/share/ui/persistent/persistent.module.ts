import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { AccountCardComponent } from './account-card/account-card.component';



@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    AccountCardComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    HeaderComponent,
    FooterComponent
  ]
})
export class PersistentModule { }
