import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { TokenDetailsService } from 'src/app/share/data-access/services/auth/token-details.service';

@Component({
  selector: 'account-card',
  templateUrl: './account-card.component.html',
  styleUrls: ['./account-card.component.scss']
})
export class AccountCardComponent {
  @Input() username: string | null = 'username'

  onLogOff(){
    this.tokenService.removeToken()
    this.router.navigate(['/login'])
  }

  constructor(public router: Router, private tokenService: TokenDetailsService){}
}
