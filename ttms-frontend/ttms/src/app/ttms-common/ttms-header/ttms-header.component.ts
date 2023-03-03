import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ttms-header',
  templateUrl: './ttms-header.component.html',
  styleUrls: ['./ttms-header.component.scss']
})
export class TtmsHeaderComponent implements OnInit {

  constructor(private router: Router){}

  ngOnInit(): void {
  }

  goToMealsForm(){
    this.router.navigate(['/meals']);
  }

}
