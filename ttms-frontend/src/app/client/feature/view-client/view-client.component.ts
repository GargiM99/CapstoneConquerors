import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { IClientBasics } from '../../data-access/types/client/client-basic.inteface';
import { TClientSearch } from '../../data-access/types/client/client-search.type';
import { IAppState } from 'src/app/share/data-access/types/app-state.interface';
import { Store, select } from '@ngrx/store';
import { clientBasicsSelector, clientErrorSelector, clientIsLoadingSelector } from '../../data-access/redux/client/client-selectors';

@Component({
  selector: 'app-view-client',
  templateUrl: './view-client.component.html',
  styleUrls: ['./view-client.component.scss']
})
export class ViewClientComponent {
  clientIsLoading$: Observable<boolean>
  clientError$: Observable<HttpErrorResponse | Error | null>
  clients$: Observable<IClientBasics[] | null>

  searchValue: string = ''
  fieldChoice: TClientSearch = 'firstname'

  handleSearch(searchValue: string): void{
    this.searchValue = searchValue;
  }

  handleFieldChange(fieldChoice: TClientSearch): void{
    this.fieldChoice = fieldChoice
  }

  constructor(private store: Store<IAppState>){
    this.clientIsLoading$ = this.store.pipe(select(clientIsLoadingSelector))
    this.clientError$ = this.store.pipe(select(clientErrorSelector))
    this.clients$ = this.store.pipe(select(clientBasicsSelector))
  } 
}
