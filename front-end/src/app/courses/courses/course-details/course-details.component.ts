import {Component, Input, OnInit } from '@angular/core';
import { Course } from '../../../../models/course';
import { CourseService } from '../../../../services/course/course.service';

@Component({
  selector: 'app-course-details',
  templateUrl: './course-details.component.html',
  styleUrls: ['./course-details.component.css']
})
export class CourseDetailsComponent implements OnInit {

  @Input()
  course: Course;
  private courseList: Course[] = [];

  constructor(public courseService: CourseService) {
    this.courseService.courses$.subscribe((courses) => this.courseList = courses);
  }

  ngOnInit() {
    this.courseService.getCourses();
  }
  public editValues() {
    const title = ((document.getElementById('title') as HTMLInputElement).value);
    // this.courseService.changeName(this.course, title);
  }
  public deleteCourse() {
    this.courseService.deleteCourse(this.course);
    this.courseService.getCourses();
  }

}
