import { Component, OnInit } from '@angular/core';
import {NgbCalendar, NgbDate} from '@ng-bootstrap/ng-bootstrap';
import {Appointment} from '../../../models/appointment';
import {AppointmentService} from '../../../services/appointment/appointment.service';
import {User} from '../../../models/users';
import {UsersService} from '../../../services/users/users.service';

@Component({
  selector: 'app-appointment-list',
  templateUrl: './appointment-list.component.html',
  styleUrls: ['./appointment-list.component.scss']
})
export class AppointmentListComponent implements OnInit {

  public currentUser: User;


  appointmentList: Appointment[] = [];

  hoveredDate: NgbDate;

  fromDate: NgbDate;
  toDate: NgbDate;

  constructor(calendar: NgbCalendar, public appointmentService: AppointmentService, public userService: UsersService) {
    this.fromDate = calendar.getToday();
    this.toDate = calendar.getNext(calendar.getToday(), 'd', 10);

    this.appointmentService.appointments$.subscribe((appointments) => {
      this.appointmentList = appointments;
    });
    this.currentUser = this.userService.getCurrentUser();

  }

  ngOnInit() {
  }

  onDateSelection(date: NgbDate) {
    if (!this.fromDate && !this.toDate) {
      this.fromDate = date;
    } else if (this.fromDate && !this.toDate && date.after(this.fromDate)) {
      this.toDate = date;
    } else {
      this.toDate = null;
      this.fromDate = date;
    }
  }

  isHovered(date: NgbDate) {
    return this.fromDate && !this.toDate && this.hoveredDate && date.after(this.fromDate) && date.before(this.hoveredDate);
  }

  isInside(date: NgbDate) {
    return date.after(this.fromDate) && date.before(this.toDate);
  }

  isRange(date: NgbDate) {
    return date.equals(this.fromDate) || date.equals(this.toDate) || this.isInside(date) || this.isHovered(date);
  }

  archiveAppointment(appointment: Appointment) {
    this.appointmentService.archiveAppointment(appointment);
  }

  editAppointment(appointment: Appointment) {
    console.log('test');
    this.appointmentService.updateAppointment(appointment);
  }

  joinAppointment(appointment: Appointment) {
    this.appointmentService.joinAppointment(appointment, this.currentUser);
  }

  filterAppointment(appointment: Appointment, fromDate: NgbDate, toDate: NgbDate): boolean {
    if (fromDate == null || toDate == null) {
      return null;
    }
    const fromDateObj = new Date(fromDate.year, fromDate.month - 1, fromDate.day);
    const toDateObj = new Date(toDate.year, toDate.month - 1, toDate.day);
    const dateObj = new Date(appointment.date);

    return dateObj.valueOf() >= fromDateObj.valueOf() && dateObj.valueOf() <= toDateObj.valueOf();
  }
}

