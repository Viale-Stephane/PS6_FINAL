import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Course} from '../../../../models/course';
import {User} from '../../../../models/users';
import {CourseService} from '../../../../services/course/course.service';
import {UsersService} from '../../../../services/users/users.service';

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.css']
})
export class CourseListComponent implements OnInit {

  @Input()
  year: string;

  @Input()
  specialty?: string;

  public courseList: Course[] = [];
  public sortedCourseList: Course[][][] = [];

  private courseClicked = null;
  public currentUser: User;

  private wantToCreate = false;
  private wantToModify = false;
  private firstSemester: string;
  private secondSemester: string;


  constructor(private route: ActivatedRoute, public courseService: CourseService, public userService: UsersService) {
    this.courseService.getCourses();
    this.currentUser = this.userService.getCurrentUser();
    this.courseService.courses$.subscribe( (courses: Course[]) => {
      this.courseList = courses;
      for (let i = 5; i < 11; i++) {
        this.sortedCourseList[i] = this.courseService.sortCourse(i);
      }
    });
    this.userService.currentUser$.subscribe((currentUser) => this.currentUser = currentUser);
  }

  ngOnInit() {
    this.route.paramMap.subscribe( params => {
      this.year = params.get('year');
      this.specialty = params.get('specialty');
      switch (this.year) {
        case '3' : this.firstSemester = '5'; this.secondSemester = '6'; break;
        case '4' : this.firstSemester = '7'; this.secondSemester = '8'; break;
        case '5' : this.firstSemester = '9' ; this.secondSemester = '10'; break;
      }
      this.courseClicked = this.courseService.getFirstCourseTitleBySemester(this.firstSemester);
      if (this.courseClicked == null) {
        this.courseClicked = this.courseService.getFirstCourseTitleBySemester(this.secondSemester);
      }
    });
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
    if (this.courseClicked === undefined) {
      this.courseClicked = this.courseService.getFirstCourseTitleBySemester(this.firstSemester);
    }
    this.wantToCreate = false;
    console.log('dans le getclickedcourse : ', this.courseClicked);
    return this.courseService.getCourseByName(this.courseClicked);
  }

  public deleteCourse() {
    this.courseService.deleteCourse(this.courseService.getCourseByName(this.courseClicked));
    for (const course of this.courseList) {
      if (course.year === this.firstSemester || course.year === this.secondSemester) {
        this.courseClicked = course.title;
        break;
      }
    }
  }

  public updateCourse(course: Course) {
    this.courseService.updateCourse(course);
    this.courseService.getCourses();
    this.wantToModify = false;
  }

  public addCourse(course: Course) {
    this.courseService.addCourse(course);
    if (course.year === this.firstSemester || course.year === this.secondSemester) {
      this.courseClicked = course.title;
    }
    console.log(this.courseClicked);
    this.wantToCreate = false;
  }

  public intFirstSemester() {
    return parseInt(this.firstSemester, 10);
  }
  public intSecondSemester() {
    return parseInt(this.secondSemester, 10);
  }
}
