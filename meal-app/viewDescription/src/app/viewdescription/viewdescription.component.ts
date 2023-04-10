import { Component, ElementRef, HostListener, Input, Renderer2, TemplateRef, ViewContainerRef} from '@angular/core';

@Component({
  selector: 'app-viewdescription',
  templateUrl: './viewdescription.component.html',
  styleUrls: ['./viewdescription.component.css']
})
export class ViewdescriptionComponent {
  adults: number = 0;
  children: number = 0;
  days: number = 0;
  FulldaysPerService: number = 0;
  QuickdaysPerService: number = 0;
  snacksPreference: boolean = true;
  mealPrice: number = 0;
  snackPrice: number=0;
  totalPrice: number=0;
  totalPricePerDay: number=0;
  adultsError: string = '';
  childrenError: string = '';
  daysError: string = '';
  FulldaysPerServiceError: string = '';
  QuickdaysPerServiceError: string = '';
  daysPerServiceError : string = '';
  
  @HostListener('mouseover')
  mouseover(){



  }
  @HostListener('mouseout')
  mouseout(){

  }
}
