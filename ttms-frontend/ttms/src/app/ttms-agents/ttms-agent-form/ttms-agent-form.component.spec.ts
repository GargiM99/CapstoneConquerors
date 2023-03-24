import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TtmsAgentFormComponent } from './ttms-agent-form.component';

describe('TtmsAgentFormComponent', () => {
  let component: TtmsAgentFormComponent;
  let fixture: ComponentFixture<TtmsAgentFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TtmsAgentFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TtmsAgentFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
