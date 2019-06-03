import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpecialtyModifyComponent } from './specialty-modify.component';

describe('SpecialtyModifyComponent', () => {
  let component: SpecialtyModifyComponent;
  let fixture: ComponentFixture<SpecialtyModifyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpecialtyModifyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpecialtyModifyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
