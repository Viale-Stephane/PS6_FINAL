import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CourseService} from '../../../../services/course/course.service';
import { Course } from '../../../../models/course';
import {SpecialtyService} from '../../../../services/specialty/specialty.service';
import {Specialty} from '../../../../models/specialty';

@Component({
  selector: 'app-course-empty',
  templateUrl: './course-empty.component.html',
  styleUrls: ['./course-empty.component.css']
})
export class CourseEmptyComponent implements OnInit {

  @Output()
  addCourse: EventEmitter<Course> = new EventEmitter<Course>();

  public courseForm: FormGroup;

  public COURSE_LIST: Course[] = [];
  public SPECIALTY_LIST: Specialty[] = [];
  constructor(public formBuilder: FormBuilder, public courseService: CourseService, public specialtyService: SpecialtyService) {
    this.courseForm = this.formBuilder.group({
      title: [],
      ects: [],
      hour: [],
      year: [],
      spe: [],
      description: [],
      optional: [],
      courseConc: []
    });
  }

  ngOnInit() {
    this.courseService.courses$.subscribe((course) => this.COURSE_LIST = course);
    this.specialtyService.specialty$.subscribe((specialty) => this.SPECIALTY_LIST = specialty);
  }

  public addCourseClick() {
    const courseToCreate: Course = this.courseForm.getRawValue() as Course;
    courseToCreate.courseConc = [];
    if (courseToCreate.spe == null) {
      courseToCreate.spe = 'aucun';
    }
    if(courseToCreate.description == null) {
      courseToCreate.description = 'Un cours de ' + courseToCreate.title;
    }
    if (courseToCreate.optional) {
      courseToCreate.optional = '1';
    } else {
      courseToCreate.optional = '0';
    }
    if (this.courseForm.getRawValue().courseConc == null) {
      courseToCreate.courseConc[0] = -1;
    } else {
      courseToCreate.courseConc[0] = parseInt(this.courseForm.getRawValue().courseConc, 10);
    }
    if (courseToCreate.title == null || courseToCreate.hour == null || courseToCreate.ects == null || courseToCreate.year == null) {
      alert("Attention un de ces champs est vide : title, ects, heures ou semestre");
    } else {
      courseToCreate.ects = courseToCreate.ects.toString();
      courseToCreate.hour = courseToCreate.hour.toString();
      this.addCourse.emit(courseToCreate);
    }
  }

  public isLastYear() {
    return this.courseForm.getRawValue().year === '9' || this.courseForm.getRawValue().year === '10';
  }
  public updatedList() {
    const tempList: Course[] = [];
    for (const tempCourse of this.COURSE_LIST) {
      if (tempCourse.year === this.courseForm.getRawValue().year) {
        tempList.push(tempCourse);
      }
    }
    return tempList;
  }
}
