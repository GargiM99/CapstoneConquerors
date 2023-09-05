import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TtmsMealFormComponent } from './ttms-meal-form.component';

describe('TtmsMealFormComponent', () => {
  let component: TtmsMealFormComponent;
  let fixture: ComponentFixture<TtmsMealFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TtmsMealFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TtmsMealFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
