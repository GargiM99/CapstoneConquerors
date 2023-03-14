import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MealCalComponent } from './meal-cal.component';

describe('MealCalComponent', () => {
  let component: MealCalComponent;
  let fixture: ComponentFixture<MealCalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MealCalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MealCalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
