import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TtmsAgentListComponent } from './ttms-agent-list.component';

describe('TtmsAgentListComponent', () => {
  let component: TtmsAgentListComponent;
  let fixture: ComponentFixture<TtmsAgentListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TtmsAgentListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TtmsAgentListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
