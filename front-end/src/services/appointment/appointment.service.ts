import { Injectable } from '@angular/core';
import {Appointment} from '../../models/appointment';
import {BehaviorSubject} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from '../../models/users';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  private appointmentList: Appointment[] = [];

  public appointments$: BehaviorSubject<Appointment[]> = new BehaviorSubject(this.appointmentList);

  private url = 'http://localhost:9428/api/appointments';

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) {
    this.getAppointments();
  }

  getAppointments() {
    this.http.get<Appointment[]>(this.url).subscribe((appointments: Appointment[]) => {
        appointments.sort(this.compare);
        this.appointments$.next(appointments);
        this.appointmentList = appointments;
    });
  }

  compare(a: Appointment, b: Appointment) {
      const aDateObj = new Date(a.date);
      const bDateObj = new Date(b.date);
      if (aDateObj.valueOf() < bDateObj.valueOf()) {
          return -1;
      }
      if (aDateObj.valueOf() > bDateObj.valueOf()) {
          return 1;
      }
      return 0;
  }

  updateAppointment(appointment: Appointment) {
    const urlWithId = this.url + '/' + appointment.id;
    this.http.put<Appointment>(urlWithId, appointment, this.httpOptions).subscribe((_appointment) => this.getAppointments());
  }

  deleteAppointment(appointment: Appointment) {
    const urlWithId = this.url + '/' + appointment.id;
    this.http.delete<Appointment>(urlWithId, this.httpOptions).subscribe((_appointment) => this.getAppointments());
  }

  removeStudentFromTicket(id: number, appointment: Appointment) {
    appointment.studentsID.splice(appointment.studentsID.indexOf(id), 1);
    this.updateAppointment(appointment);
  }

  archiveAppointment(appointment: Appointment) {
    // appointment.active = false;
    // this.updateAppointment(appointment);
    this.deleteAppointment(appointment);
  }

  joinAppointment(appointment: Appointment, currentUser: User) {
    this.addStudentToAppointment(appointment, currentUser);
    this.updateAppointment(appointment);
  }

  addStudentToAppointment(appointment: Appointment, student: User) {
    appointment.studentsID.push(student.id);
  }

  addAppointment(appointment: Appointment) {
    this.http.post<Appointment>(this.url, appointment, this.httpOptions).subscribe((_appointment) => this.getAppointments());
  }

  getDayFromString(date: string): number {
    return Number(date.split('-')[2]);
  }
  getMonthFromString(date: string): number {
    return Number(date.split('-')[1]);
  }
  getYearFromString(date: string): number {
    return Number(date.split('-')[0]);
  }
}
