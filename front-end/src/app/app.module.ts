import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import { AppComponent } from './app.component';
import { CourseService } from '../services/course/course.service';
import { SpecialtyService } from '../services/specialty/specialty.service';
import { ReactiveFormsModule } from '@angular/forms';
import { HeaderOfflineComponent } from './header';
import { HeaderOnlineComponent } from './header';
import { HomeComponent } from './home';
import { AppRoutingModule } from './app-routing.module';
import { ForeignInformationsComponent } from './foreign-informations';
import { RegisterComponent } from './register';
import { LoginComponent } from './login';
import { CoursesComponent } from './courses';
import { CoursesSI3Component } from './courses/courses/courses-si3';
import { CoursesSI4Component } from './courses/courses/courses-si4';
import { CoursesSI5Component } from './courses/courses/courses-si5';
import { CourseDetailsComponent } from './courses/courses/course-details';
import { CourseEmptyComponent } from './courses/courses/course-empty/course-empty.component';
import { StudentsComponent } from './appointments/students/students.component';
import { HttpClientModule } from '@angular/common/http';
import { TeachersComponent } from './appointments/teachers/teachers.component';
import { UsersService } from 'src/services/users/users.service';
import { CourseModifyComponent } from './courses/courses/course-modify/course-modify.component';
import { SpeDetailsComponent } from './courses/specialties/spe-details/spe-details.component';
import { SpecialtiesComponent } from './courses/specialties/specialties.component';
import { SpecialtyModifyComponent } from './courses/specialties/specialty-modify/specialty-modify.component';
import { SpecialtyEmptyComponent } from './courses/specialties/specialty-empty/specialty-empty.component';
import { MyDynamicChartComponent } from './my-dynamic-chart/my-dynamic-chart.component';
import { CourseListComponent } from './courses/courses/course-list/course-list.component';
import {AppointmentComponent} from './appointments/appointment/appointment.component';
import {AppointmentsComponent} from './appointments';
import { AppointmentListComponent } from './appointments/appointment-list/appointment-list.component';
import { AppointmentFormComponent } from './appointments/appointment-form/appointment-form.component';
import {SchoolService} from '../services/school/school.service';
import {AppointmentService} from '../services/appointment/appointment.service';

@NgModule({
  declarations: [
    AppComponent,
    CoursesComponent,
    ForeignInformationsComponent,
    HomeComponent,
    RegisterComponent,
    LoginComponent,
    HeaderOfflineComponent,
    HeaderOnlineComponent,
    CoursesSI3Component,
    CoursesSI4Component,
    CoursesSI5Component,
    CourseDetailsComponent,
    CourseEmptyComponent,
    StudentsComponent,
    TeachersComponent,
    CourseModifyComponent,
    SpeDetailsComponent,
    SpecialtiesComponent,
    SpecialtyModifyComponent,
    SpecialtyEmptyComponent,
    MyDynamicChartComponent,
    AppointmentComponent,
    AppointmentsComponent,
    CourseListComponent,
    AppointmentListComponent,
    AppointmentFormComponent, // All the components needs to be declared
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    ReactiveFormsModule, // Import all dependencies
    HttpClientModule,
    ChartsModule,
    NgbModule,
  ],
  providers: [CourseService, UsersService, SpecialtyService, SchoolService, AppointmentService], // All the services need to be provided
  bootstrap: [AppComponent]
})
export class AppModule {
}
