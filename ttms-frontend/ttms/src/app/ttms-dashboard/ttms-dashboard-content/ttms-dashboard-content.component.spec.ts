import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TtmsDashboardContentComponent } from './ttms-dashboard-content.component';

describe('TtmsDashboardContentComponent', () => {
  let component: TtmsDashboardContentComponent;
  let fixture: ComponentFixture<TtmsDashboardContentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TtmsDashboardContentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TtmsDashboardContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
