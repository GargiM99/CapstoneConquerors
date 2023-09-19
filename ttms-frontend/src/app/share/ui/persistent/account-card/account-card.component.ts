import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'account-card',
  templateUrl: './account-card.component.html',
  styleUrls: ['./account-card.component.scss']
})
export class AccountCardComponent {
  @Input() username: string | null = 'username'

  constructor(public router: Router){}
}
