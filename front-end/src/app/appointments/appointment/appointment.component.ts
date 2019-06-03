import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {User} from '../../../models/users';
import {Appointment} from '../../../models/appointment';
import {UsersService} from '../../../services/users/users.service';
import {ModalDismissReasons, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder, FormGroup} from '@angular/forms';
import {AppointmentService} from '../../../services/appointment/appointment.service';

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.scss']
})
export class AppointmentComponent implements OnInit {

  public currentUser: User;
  public appointmentForm: FormGroup;
  public closeResult: string;


  @Input()
  appointment: Appointment;

  @Output()
  archiveAppointment: EventEmitter<Appointment> = new EventEmitter<Appointment>();

  @Output()
  joinAppointment: EventEmitter<Appointment> = new EventEmitter<Appointment>();

  @Output()
  editAppointment: EventEmitter<Appointment> = new EventEmitter<Appointment>();


  constructor(public formBuilder: FormBuilder, public userService: UsersService, private modalService: NgbModal, public appointmentService: AppointmentService) {
    this.appointmentForm = this.formBuilder.group({
      title: [],
      desc: [],
      places: [],
      date: [],
      startTime: [],
      endTime: [],
    });
    this.currentUser = this.userService.getCurrentUser();
  }

  ngOnInit() {
  }

  getNameWithId(id: Number) {
    return this.userService.getUserById(id).username;
  }

  removeStudent(id: number, appointment: Appointment) {
    this.appointmentService.removeStudentFromTicket(id, appointment);
  }

  archive() {
    this.archiveAppointment.emit(this.appointment);
  }

  join() {
    this.joinAppointment.emit(this.appointment);
  }

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
    this.editAppointment.emit(this.appointment);
  }

  validateEditionOfAppointment() {
    const value = this.appointmentForm.getRawValue();
    if (value.title !== null) {
      this.appointment.title = value.title;
    }
    if (value.desc !== null) {
      this.appointment.desc = value.desc;
    }
    if (value.places !== null) {
      this.appointment.places = value.places;
    }
    if (value.startTime !== null) {
      this.appointment.startTime = value.startTime;
    }
    if (value.endTime !== null) {
      this.appointment.endTime = value.endTime;
    }
    if (value.date !== null) {
      const dateObj = new Date(this.appointmentService.getYearFromString(value.date.toString()),
          this.appointmentService.getMonthFromString(value.date.toString()) - 1,
          this.appointmentService.getDayFromString(value.date.toString()));
      this.appointment.date = dateObj;
    }

      this.appointmentService.updateAppointment(this.appointment);
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return  `with: ${reason}`;
    }
  }
}
