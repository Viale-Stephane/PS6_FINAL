import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home';
import { AppointmentsComponent } from './appointments';
import { ForeignInformationsComponent } from './foreign-informations';
import { RegisterComponent } from './register';
import { LoginComponent } from './login';
import { CoursesComponent } from './courses';
import { CoursesSI3Component } from './courses/courses/courses-si3';
import { CoursesSI4Component } from './courses/courses/courses-si4';
import { CoursesSI5Component } from './courses/courses/courses-si5';
import { StudentsComponent } from './appointments/students/students.component';
import {TeachersComponent} from './appointments/teachers/teachers.component';
import { SpecialtiesComponent } from './courses/specialties/specialties.component';
import { SpecialtyEmptyComponent} from './courses/specialties/specialty-empty/specialty-empty.component';
import { CourseListComponent } from './courses/courses/course-list/course-list.component';

const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'login', component: LoginComponent},
    {path: 'register', component: RegisterComponent},
    {path: 'courses', component: CoursesComponent},
    {path: 'appointments', component: AppointmentsComponent},
    {path: 'appointments/students', component: StudentsComponent},
    {path: 'appointments/teachers', component: TeachersComponent},
    {path: 'foreign-informations/teachers', component: ForeignInformationsComponent },
    {path: 'foreign-informations/students', component: ForeignInformationsComponent },
    {path: 'courses/coursesSI3', component: CoursesSI3Component},
    {path: 'courses/coursesSI4', component: CoursesSI4Component},
    {path: 'courses/coursesSI5', component: CoursesSI5Component},
    {path: 'courses/coursesSI5/specialties/:name', component: SpecialtiesComponent},
    {path: 'courses/:year', component: CourseListComponent},
    {path: 'courses/:year/:specialty', component: CourseListComponent},
    {path: 'addSpecialty', component: SpecialtyEmptyComponent},
    {path: '**', redirectTo: '', component: HomeComponent }// should be 404

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
