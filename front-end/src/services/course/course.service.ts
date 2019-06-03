import { Course} from '../../models/course';
import { COURSES_MOCKED} from '../../mocks/courses.mock';
import {BehaviorSubject, of} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {computeStyle} from '@angular/animations/browser/src/util';

@Injectable({
    providedIn: 'root'
})

export class CourseService {
    private courseList: Course[] = [];

    private url = 'http://localhost:9428/api/courses';

    public courses$: BehaviorSubject<Course[]> = new BehaviorSubject(this.courseList);

    constructor(private http: HttpClient) {
        this.getCourses();
    }
    public getCourses() {
        this.http.get<Course[]>(this.url).subscribe((courses) => {
            this.courseList = courses;
            this.courses$.next(courses);
        });
    }
    public getCourseByName(name) {
        for (const course of this.courseList) {
            if (course.title === name) {
                return course;
            }
        }
    }

    public getCourseById(id: Number) {
        for (const course of this.courseList) {
            if (course.id === id) {
                return course;
            }
        }
    }

    public getEctsSemester(semester) {
        const sortedList = this.sortCourse(semester);
        let ects = 0;
        for (const courseList of sortedList) {
            for (const course of courseList) {
                if (course.year === semester) {
                    if (course.optional === '0') {
                        ects += parseInt(course.ects, 10);
                    } else {
                        ects += parseInt(course.ects, 10);
                        break;
                    }
                }
            }
        }
        // console.log(ects);
        return ects;
    }
    addCourse(course: Course) {
        return this.http.post(this.url, course).subscribe(() => this.getCourses());
    }
    deleteCourse(course: Course) {
        this.http.delete<Course>(this.url + '/' + course.id).subscribe(() => this.getCourses());
        this.courses$.next(this.courseList);
    }
    updateCourse(course: Course) {
        const urlWithId = this.url + '/' + course.id;
        this.http.put<Course>(urlWithId, course).subscribe(() => this.getCourses());
    }

    getFirstCourseTitleBySemester(semester) {
        for (const course of this.courseList) {
            if (course.year === semester) {
                return course.title;
            }
        }
    }
    sortCourse(semester: Number) {
        const copyCourseList = this.courseList;
        const splitedCourses: Course[][] = [];
        const bannedValues: Number[] = [];
        for (let i = 0; i < copyCourseList.length; i++) {
            if (copyCourseList[i].year !== semester.toString()) {
                bannedValues.push(i);
            }
        }
        for (let i = 0; i < copyCourseList.length; i++) {
            const ordonnedList: Course[] = [];
            if (!bannedValues.includes(i)) {
                if (copyCourseList[i].courseConc.length === 1) {
                    const course = this.getCourseById(copyCourseList[i].id);
                    ordonnedList.push(course);
                    bannedValues.push(i);
                }
                splitedCourses.push(ordonnedList);
            }
        }

        for (let i = 0; i < copyCourseList.length; i++) {
            const ordonnedList: Course[] = [];
            if (!bannedValues.includes(i)) {
                if (copyCourseList[i].courseConc != null && copyCourseList[i].courseConc !== undefined) {
                    for (let j = 0; j < copyCourseList[i].courseConc.length; j++) {
                        const course = this.getCourseById(copyCourseList[i].courseConc[j]);
                        ordonnedList.push(course);
                        bannedValues.push(i);
                        const indexOfCourse = copyCourseList.indexOf(course);
                        bannedValues.push(indexOfCourse);
                    }
                }
                splitedCourses.push(ordonnedList);
            }
        }
        return splitedCourses;
    }
}
