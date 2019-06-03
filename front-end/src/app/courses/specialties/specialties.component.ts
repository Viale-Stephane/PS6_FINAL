import {Component, Input, OnInit} from '@angular/core';
import {Course} from '../../../models/course';
import {ActivatedRoute} from '@angular/router';
import {Specialty} from '../../../models/specialty';
import {CourseService} from '../../../services/course/course.service';
import {SpecialtyService} from '../../../services/specialty/specialty.service';
import {UsersService} from '../../../services/users/users.service';

@Component({
  selector: 'app-specialties',
  templateUrl: './specialties.component.html',
  styleUrls: ['./specialties.component.css']
})
export class SpecialtiesComponent implements OnInit {

  @Input()
  name: string;

  private speToShow: Specialty;

  public specialtiesList: Specialty[] = [];

  private isProf = false;

  constructor(private route: ActivatedRoute, public specialtyService: SpecialtyService, public userService: UsersService) {
    this.speToShow = specialtyService.getSpecialtyByAbr(this.name);
    this.specialtyService.getSpecialties();
    this.specialtyService.specialty$.subscribe((specialties: Specialty[]) => {
      this.specialtiesList = specialties;
      for (const specialty of specialties) {
        console.log('le nom passé dans l\'url est ', this.name);
        console.log('le nom de la spé courante est ', specialty.abr);
        console.log('est ce vrai ?', specialty.abr === this.name);
        if (specialty.abr === this.name) {
          console.log('JE SUIS DANS LE IF');
          this.speToShow = specialty;
          break;
        }
      }
      console.log(this.speToShow);
    });
    if (userService.isConnectedAs() === 2) {
      this.isProf = true;
    } else{
      this.isProf = false;
    }
  }

  ngOnInit() {
    this.specialtyService.getSpecialties();
    this.route.paramMap.subscribe( params => this.name = params.get('name'));

  }

  public getSpeToShow(name: string) {
    return this.speToShow;
  }

  public updateSpecialty(specialty: Specialty) {
    this.specialtyService.updateSpecialty(specialty);
    this.specialtyService.getSpecialties();
  }

}
