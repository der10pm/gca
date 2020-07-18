import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OderConfirmComponent } from './oder-confirm.component';

describe('OderConfirmComponent', () => {
  let component: OderConfirmComponent;
  let fixture: ComponentFixture<OderConfirmComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OderConfirmComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OderConfirmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
