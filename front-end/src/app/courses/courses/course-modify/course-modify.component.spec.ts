import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CourseModifyComponent } from './course-modify.component';

describe('CourseModifyComponent', () => {
  let component: CourseModifyComponent;
  let fixture: ComponentFixture<CourseModifyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CourseModifyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CourseModifyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
