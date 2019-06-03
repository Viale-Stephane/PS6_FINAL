import { Component, OnInit } from '@angular/core';
import {UsersService} from '../../../../services/users/users.service';
import {SpecialtyService} from '../../../../services/specialty/specialty.service';
import {Specialty} from '../../../../models/specialty';

@Component({
  selector: 'app-courses-si5',
  templateUrl: './courses-si5.component.html',
  styleUrls: ['./courses-si5.component.css']
})
export class CoursesSI5Component implements OnInit {

  private isProf = false;

  public specialtyList: Specialty[] = [];

  constructor(public userService: UsersService, public specialtyService: SpecialtyService) {
    if (userService.isConnectedAs() === 2) {
      this.isProf = true;
    } else {
      this.isProf = false;
    }
    console.log(this.userService.getCurrentUser());
    this.specialtyService.getSpecialties();
    this.specialtyService.specialty$.subscribe((specialities) => this.specialtyList = specialities);
  }

  ngOnInit() {
  }

  public getMessage() {
    return 'je test des choses';
  }

  public getAbr(specialty: Specialty) {
    return specialty.abr;
  }

  public getProf() {
    if (this.userService.isConnectedAs() === 2) {
      this.isProf = true;
    } else {
      this.isProf = false;
    }
    return this.isProf;
  }

  public deleteSpe(speciality: Specialty) {
    this.specialtyService.deleteSpecialty(speciality);
  }

}
