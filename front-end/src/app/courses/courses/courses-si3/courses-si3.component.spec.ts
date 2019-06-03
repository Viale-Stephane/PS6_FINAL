import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CoursesSI3Component } from './courses-si3.component';

describe('CoursesSI3Component', () => {
  let component: CoursesSI3Component;
  let fixture: ComponentFixture<CoursesSI3Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CoursesSI3Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CoursesSI3Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
