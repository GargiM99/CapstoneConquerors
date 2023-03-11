import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TtmsLoginFormComponent } from './ttms-login-form.component';

describe('TtmsLoginFormComponent', () => {
  let component: TtmsLoginFormComponent;
  let fixture: ComponentFixture<TtmsLoginFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TtmsLoginFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TtmsLoginFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
