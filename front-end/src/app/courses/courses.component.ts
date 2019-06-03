import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Location} from '@angular/common';
import {SpecialtyService} from '../../services/specialty/specialty.service';
import {Specialty} from '../../models/specialty';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss']
})
export class CoursesComponent implements OnInit {
  @Output()
  si3Selected: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Output()
  si4Selected: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Output()
  si5Selected: EventEmitter<boolean> = new EventEmitter<boolean>();

  public SPECIALTY_LIST: Specialty[] = [];

  constructor(private location: Location, public specialtyService: SpecialtyService) {
  }

  ngOnInit() {
    this.specialtyService.specialty$.subscribe((specialty) => this.SPECIALTY_LIST = specialty);
  }

  selectSI3() {
    console.log('SI3');
    this.si3Selected.emit(true);
  }

  selectSI4() {
    console.log('SI4');
    this.si4Selected.emit(true);
  }

  selectSI5() {
    console.log('SI5');
    this.si5Selected.emit(true);
  }
}
