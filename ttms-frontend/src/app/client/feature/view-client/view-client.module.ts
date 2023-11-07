import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViewClientComponent } from './view-client.component';
import { ClientUtilityBarComponent } from '../../ui/client-utility-bar/client-utility-bar.component';
import { ClientFilterPipe } from "../../data-access/pipe/client-filter.pipe";
import { ClientCardComponent } from '../../../share/ui/cards/client-card/client-card.component';

@NgModule({
    declarations: [ViewClientComponent],
    imports: [
        CommonModule,
        ClientUtilityBarComponent,
        ClientCardComponent,
        ClientFilterPipe
    ],
    exports: [ViewClientComponent]
})
export class ViewClientModule { }
