import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {User} from '../../../models/users';
import {UsersService} from '../../../services/users/users.service';
import {Appointment} from '../../../models/appointment';
import {AppointmentService} from '../../../services/appointment/appointment.service';
import {range} from 'rxjs';

@Component({
  selector: 'app-appointment-form',
  templateUrl: './appointment-form.component.html',
  styleUrls: ['./appointment-form.component.scss']
})
export class AppointmentFormComponent implements OnInit {

  public appointmentForm: FormGroup;
  public currentUser: User;


  constructor(public formBuilder: FormBuilder, public appointmentService: AppointmentService, public userService: UsersService) {
    this.appointmentForm = this.formBuilder.group({
      title: [],
      desc: [],
      places: [],
      date: [],
      startTime: [],
      endTime: [],
      repeat: [],
    });
    this.currentUser = this.userService.getCurrentUser();
  }

  ngOnInit() {
  }

  addAppointment() {
    const repeat = this.appointmentForm.getRawValue().repeat;

    for (let i = 0; i < repeat; i++) {
      const newAppointment = this.appointmentForm.getRawValue();
      newAppointment.teacherID = this.currentUser.id;
      newAppointment.studentsID = [];
      delete newAppointment.repeat;
      const dateObj = new Date(this.appointmentService.getYearFromString(newAppointment.date.toString()),
          this.appointmentService.getMonthFromString(newAppointment.date.toString()) - 1,
          this.appointmentService.getDayFromString(newAppointment.date.toString()));
      dateObj.setDate(dateObj.getDate() + (7 * i));
      newAppointment.date = dateObj;

      this.appointmentService.addAppointment(newAppointment);
    }
  }

}
