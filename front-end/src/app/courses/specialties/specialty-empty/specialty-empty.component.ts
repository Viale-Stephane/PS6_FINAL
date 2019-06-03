import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Specialty} from '../../../../models/specialty';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SpecialtyService} from '../../../../services/specialty/specialty.service';
import {Course} from '../../../../models/course';

@Component({
  selector: 'app-specialty-empty',
  templateUrl: './specialty-empty.component.html',
  styleUrls: ['./specialty-empty.component.css']
})
export class SpecialtyEmptyComponent implements OnInit {

  public specialtyForm: FormGroup;

  public SPECIALTY_LIST: Specialty[] = [];

  constructor(public formBuilder: FormBuilder, public specialtyService: SpecialtyService) {
    this.specialtyForm = this.formBuilder.group({
      title: [],
      abr: [],
      description: []
    });
  }

  ngOnInit() {
    this.specialtyService.specialty$.subscribe((specialties) => this.SPECIALTY_LIST = specialties);
  }

  public addSpecialty() {
    const specialtyToCreate: Specialty = this.specialtyForm.getRawValue() as Specialty;
    specialtyToCreate.title.substr(0, specialtyToCreate.title.indexOf(' '));
    this.specialtyService.addSpecialty(specialtyToCreate).subscribe(specialty => {
      this.SPECIALTY_LIST.push(specialty);
    });
  }

}

