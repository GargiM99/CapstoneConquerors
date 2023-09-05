import { Component, OnInit } from '@angular/core';
import { IAppState } from './share/data-access/types/app-state.interface';
import { Store, select } from '@ngrx/store';
import { Observable, of } from 'rxjs';
import { detailSelector } from './share/data-access/redux/auth/token-selectors';
import { detailSelector as profileDetail, isLoadingSelector as profileLoading } from './share/data-access/redux/profile/profile-selectors';
import { ITokenDetail } from './share/data-access/types/auth/token-details.interface';
import { Location } from '@angular/common';
import * as TokenAction from './share/data-access/redux/auth/token-actions';
import { IProfileDetails } from './share/data-access/types/profile/profile-details.interface';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  tokenDetails$: Observable<ITokenDetail | null>
  profileDetails$: Observable<IProfileDetails | null>
  profileLoading$: Observable<boolean> 

  constructor(private store: Store<IAppState>, protected location: Location){
    this.profileDetails$ = this.store.pipe(select(profileDetail))
    this.tokenDetails$ = this.store.pipe(select(detailSelector))
    this.profileLoading$ = this.store.pipe(select(profileLoading))
  }

  ngOnInit(): void { 
    let isChecked = false
    this.tokenDetails$.forEach((tokenDetail) => {
      if (tokenDetail == null || tokenDetail.token == null && !isChecked){
        isChecked = true
        this.store.dispatch(TokenAction.getTokenDetails( {loginDetails: null} ))
      }
    })
  }

  title = 'ttms-frontend';
}
