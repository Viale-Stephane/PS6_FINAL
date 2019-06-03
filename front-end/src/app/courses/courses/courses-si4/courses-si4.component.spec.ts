import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CoursesSI4Component } from './courses-si4.component';

describe('CoursesSI4Component', () => {
  let component: CoursesSI4Component;
  let fixture: ComponentFixture<CoursesSI4Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CoursesSI4Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CoursesSI4Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
