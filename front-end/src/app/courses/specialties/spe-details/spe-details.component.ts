import {Component, Input, OnInit} from '@angular/core';
import {Specialty} from '../../../../models/specialty';
import {UsersService} from '../../../../services/users/users.service';
import {SpecialtyService} from '../../../../services/specialty/specialty.service';

@Component({
  selector: 'app-spe-details',
  templateUrl: './spe-details.component.html',
  styleUrls: ['./spe-details.component.css']
})
export class SpeDetailsComponent implements OnInit {

  @Input()
  specialty: Specialty;

  public isProf = false;

  constructor(public userService: UsersService, public specialtyService: SpecialtyService) {
    if (userService.isConnectedAs() === 2) {
      this.isProf = true;
    }
  }

  ngOnInit() {
  }

  public updateSpecialty(specialty: Specialty) {
    this.specialtyService.updateSpecialty(specialty);
    this.specialtyService.getSpecialties();
  }

  public getProf() {
    if (this.userService.isConnectedAs() === 2) {
      this.isProf = true;
    } else {
      this.isProf = false;
    }
    return this.isProf;
  }
}
