import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TtmsHeaderComponent } from './ttms-header.component';

describe('TtmsHeaderComponent', () => {
  let component: TtmsHeaderComponent;
  let fixture: ComponentFixture<TtmsHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TtmsHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TtmsHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
