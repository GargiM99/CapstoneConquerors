import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgentFormsComponent } from './agent-forms.component';

describe('AgentFormsComponent', () => {
  let component: AgentFormsComponent;
  let fixture: ComponentFixture<AgentFormsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AgentFormsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgentFormsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
