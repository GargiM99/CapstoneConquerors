import { TClientSearch } from '../types/client/client-search.type';
import { IClientBasics } from '../types/client/client-basic.inteface';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'clientFilter',
  standalone: true
})
export class ClientFilterPipe implements PipeTransform {

  transform(clients: IClientBasics[] | null, searchText: string, field: TClientSearch): IClientBasics[] {

    if (!clients) return []
    if (!searchText) return clients
    searchText = searchText.toLowerCase()

    return clients.filter( it =>{
      return it[field].toLowerCase().includes(searchText)
    })
  }

}
