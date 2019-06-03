import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Specialty} from '../../../../models/specialty';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SpecialtyService} from '../../../../services/specialty/specialty.service';

@Component({
  selector: 'app-specialty-modify',
  templateUrl: './specialty-modify.component.html',
  styleUrls: ['./specialty-modify.component.css']
})
export class SpecialtyModifyComponent implements OnInit {

  @Input()
  specialty: Specialty;

  @Output()
  updateSpecialty: EventEmitter<Specialty> = new EventEmitter<Specialty>();

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

  public modifySpecialty() {
    const specialty: Specialty = this.specialty;
    if (this.specialtyForm.getRawValue().title !== null) {
      specialty.title = this.specialtyForm.getRawValue().title;
    }
    if (this.specialtyForm.getRawValue().abr !== null) {
      specialty.abr = this.specialtyForm.getRawValue().abr;
    }
    if (this.specialtyForm.getRawValue().description !== null) {
      specialty.description = this.specialtyForm.getRawValue().description;
    }
    this.updateSpecialty.emit(specialty);
  }

}
