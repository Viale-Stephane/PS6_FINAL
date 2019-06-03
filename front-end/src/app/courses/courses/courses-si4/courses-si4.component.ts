import { Component, OnInit } from '@angular/core';
import {Course} from '../../../../models/course';
import { User} from '../../../../models/users';
import { CourseService} from '../../../../services/course/course.service';
import { UsersService} from '../../../../services/users/users.service';

@Component({
  selector: 'app-courses-si4',
  templateUrl: './courses-si4.component.html',
  styleUrls: ['./courses-si4.component.css']
})
export class CoursesSI4Component implements OnInit {

  public courseList: Course[] = [];

  private courseClicked = null;
  public currentUser: User;

  private wantToCreate = false;
  private wantToModify = false;
  constructor(public courseService: CourseService, public userService: UsersService) {
    this.courseService.getCourses();
    this.courseService.courses$.subscribe( (courses: Course[]) => {
      this.courseList = courses;
      for (const course of courses) {
        if (course.year === '7') {
          this.courseClicked = course.title;
          break;
        }
      }
    });
    this.currentUser = this.userService.getCurrentUser();
    this.userService.currentUser$.subscribe((currentUser) => this.currentUser = currentUser);
  }

  ngOnInit() {
  }


  public clickCourseButton() {
    this.wantToCreate = false;
    const elementId: string = (event.target as Element).id;
    console.log(elementId);
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
      if (course.year === '7' || course.year === '8') {
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

}
