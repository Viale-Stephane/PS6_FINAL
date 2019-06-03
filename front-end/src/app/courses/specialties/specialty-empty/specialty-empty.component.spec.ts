import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpecialtyEmptyComponent } from './specialty-empty.component';

describe('SpecialtyEmptyComponent', () => {
  let component: SpecialtyEmptyComponent;
  let fixture: ComponentFixture<SpecialtyEmptyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpecialtyEmptyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpecialtyEmptyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
