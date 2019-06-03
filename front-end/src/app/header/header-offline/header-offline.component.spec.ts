import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderOfflineComponent } from './header-offline.component';

describe('HeaderOfflineComponent', () => {
  let component: HeaderOfflineComponent;
  let fixture: ComponentFixture<HeaderOfflineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HeaderOfflineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderOfflineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
