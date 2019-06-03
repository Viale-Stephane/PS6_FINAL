import { Component, OnInit } from '@angular/core';
import {Course} from '../../../../models/course';
import { User} from '../../../../models/users';
import { CourseService} from '../../../../services/course/course.service';
import { UsersService} from '../../../../services/users/users.service';

@Component({
  selector: 'app-courses-si3',
  templateUrl: './courses-si3.component.html',
  styleUrls: ['./courses-si3.component.css']
})
export class CoursesSI3Component implements OnInit {

  public courseList: Course[] = [];
  public sortedCourseList: Course[][][] = [];

  private courseClicked = null;
  public currentUser: User;

  private wantToCreate = false;
  private wantToModify = false;
  constructor(public courseService: CourseService, public userService: UsersService) {
    this.courseService.getCourses();
    this.courseService.courses$.subscribe( (courses: Course[]) => {
      this.courseList = courses;
      for (const course of courses) {
        if (course.year === '5') {
          this.courseClicked = course.title;
          break;
        }
      }
      for (let i = 5; i < 8; i++) {
        this.sortedCourseList[i] = this.courseService.sortCourse(i);
      }
      console.log(this.sortedCourseList);
    });
    this.currentUser = this.userService.getCurrentUser();
    this.userService.currentUser$.subscribe((currentUser) => this.currentUser = currentUser);
  }

  ngOnInit() {
  }
  public clickCourseButton() {
    this.wantToCreate = false;
    const elementId: string = (event.target as Element).id;
    this.courseClicked = elementId;
  }
  public clickNewCourseButton() {
    this.wantToCreate = true;
  }
  public clickModifyCourseButton() {
    this.wantToModify = !this.wantToModify;
  }
  public getCourseToModify() {
    this.wantToCreate = false;
    return this.courseService.getCourseByName(this.courseClicked);
  }

  public getClickedCourse() {
    this.wantToCreate = false;
    return this.courseService.getCourseByName(this.courseClicked);
  }

  public deleteCourse() {
    this.courseService.deleteCourse(this.courseService.getCourseByName(this.courseClicked));
    for (const course of this.courseList) {
      if (course.year === '5' || course.year === '6') {
        this.courseClicked = course.title;
        break;
      }
    }
  }

  public updateCourse(course: Course) {
    this.courseService.updateCourse(course);
    this.courseService.getCourses();
    this.courseClicked = course.title;
  }

  public addCourse(course: Course) {
    this.courseService.addCourse(course);
    this.courseService.getCourses();
    this.courseClicked = course.title;
  }
}
