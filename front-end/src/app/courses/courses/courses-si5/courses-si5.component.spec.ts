import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CoursesSI5Component } from './courses-si5.component';

describe('CoursesSI5Component', () => {
  let component: CoursesSI5Component;
  let fixture: ComponentFixture<CoursesSI5Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CoursesSI5Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CoursesSI5Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
