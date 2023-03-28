import { Component, ElementRef, HostListener, Input, Renderer2, TemplateRef, ViewContainerRef} from '@angular/core';

@Component({
  selector: 'app-viewdescription',
  templateUrl: './viewdescription.component.html',
  styleUrls: ['./viewdescription.component.css']
})
export class ViewdescriptionComponent {

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
