import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpeDetailsComponent } from './spe-details.component';

describe('SpeDetailsComponent', () => {
  let component: SpeDetailsComponent;
  let fixture: ComponentFixture<SpeDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpeDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpeDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
