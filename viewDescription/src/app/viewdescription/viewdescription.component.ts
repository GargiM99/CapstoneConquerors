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
  /*constructor(private elRef:ElementRef,
              private rendered:Renderer2,
              private viewRefContainer:ViewContainerRef){}*/

  /*@Input('viewdescription')
  viewDescription!: TemplateRef<any>;*/

 /* createviewdescription(){
    const viewdescription =this.rendered.createElement('div');
    this.rendered.addClass(viewdescription,'viewDescriptionMy');
    this.rendered.setStyle(viewdescription,'position','absolute');
    const viewRef =this.viewRefContainer.createEmbeddedView(this.viewDescription);
    viewRef.detectChanges();
    const viewdescriptionContent=viewRef.rootNodes[0];
    //this.rendered.appendChild(viewdescription,viewdescriptionContent);
    //this.rendered.appendChild(this.elRef.nativeElement,viewdescription);



    return viewdescription;
  }*/
  @HostListener('mouseover')
  mouseover(){



  }
  @HostListener('mouseout')
  mouseout(){
   /* setTimeout(() => {
      const viewdescription= this.elRef.nativeElement.querySelector('.viewDescriptionMy');
      this.rendered.removeChild(this.elRef.nativeElement,viewdescription);
     },300);*/
  }
}
