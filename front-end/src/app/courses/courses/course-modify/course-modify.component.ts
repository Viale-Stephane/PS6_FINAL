import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Course} from '../../../../models/course';
import {CourseService} from '../../../../services/course/course.service';

@Component({
  selector: 'app-course-modify',
  templateUrl: './course-modify.component.html',
  styleUrls: ['./course-modify.component.css']
})
export class CourseModifyComponent implements OnInit {

  @Input()
  course: Course;

  @Output()
  updateCourse: EventEmitter<Course> = new EventEmitter<Course>();

  public courseForm: FormGroup;

  public COURSE_LIST: Course[] = [];
  constructor(public formBuilder: FormBuilder, public courseService: CourseService) {
    this.courseForm = this.formBuilder.group({
      title: [],
      ects: [],
      hour: [],
      year: [],
      description: [],
      optional: []
    });
  }

  ngOnInit() {
    this.courseService.courses$.subscribe((course) => this.COURSE_LIST = course);
  }

  public isChecked() {
    if ( this.course.optional === '1') {
      return true;
    } return false;
  }

  public modifyCourse() {
    const course: Course = this.course;
    if (this.courseForm.getRawValue().title !== null) {
      course.title = this.courseForm.getRawValue().title;
    }
    if (this.courseForm.getRawValue().ects !== null) {
      course.ects = this.courseForm.getRawValue().ects;
    }
    if (this.courseForm.getRawValue().hour !== null) {
      course.hour = this.courseForm.getRawValue().hour;
    }
    if (this.courseForm.getRawValue().year !== null) {
      course.year = this.courseForm.getRawValue().year;
    }
    if (this.courseForm.getRawValue().description !== null) {
      course.description = this.courseForm.getRawValue().description;
    }
    if (this.courseForm.getRawValue().optional) {
      course.optional = '1';
    } else {
      course.optional = '0';
    }
    this.updateCourse.emit(course);
  }

}
