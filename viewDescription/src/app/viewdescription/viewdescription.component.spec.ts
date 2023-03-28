import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewdescriptionComponent } from './viewdescription.component';

describe('ViewdescriptionComponent', () => {
  let component: ViewdescriptionComponent;
  let fixture: ComponentFixture<ViewdescriptionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewdescriptionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewdescriptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
