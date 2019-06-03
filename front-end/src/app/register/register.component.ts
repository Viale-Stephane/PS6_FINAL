import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { UsersService } from 'src/services/users/users.service';
import { User } from 'src/models/users';
import { Location } from '@angular/common';
import {Router} from '@angular/router';
import {SchoolService} from '../../services/school/school.service';
import {School} from '../../models/school';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  public registerForm: FormGroup;
  public wantADestination = false;
  public schoolList: School[] = [];

  constructor(public schoolService: SchoolService, public formBuilder: FormBuilder, public usersService: UsersService, private location: Location, private router: Router) {
    this.registerForm = this.formBuilder.group({
      username: [''],
      password: [''],
      professor: [''],
      destination: [''],
      phoneNumber: [''],
    });
    schoolService.getSchool();
    schoolService.school$.subscribe((schools) => this.schoolList = schools);
  }

  ngOnInit() {
  }

  goBack() {
    this.location.back();
  }

  public wantToAddDestination() {
    return(this.wantADestination);
  }
  public updateWantADestination() {
    this.wantADestination = !this.wantADestination;
  }

  public isAProfessor() {
    return(this.registerForm.getRawValue().professor);
  }

  public addUser() {
    const userToAdd: User = this.registerForm.getRawValue() as User;
    if(userToAdd.username != null && userToAdd.username!=="" && userToAdd.password != null && userToAdd.password !== "" ) {
      if (!userToAdd.professor) { 
        // force false value if it does not exist
        userToAdd.professor = false;
      }
      if (userToAdd.professor) {
        userToAdd.destination = '';
      }
      if (userToAdd.destination == null || userToAdd.destination === "") {
        userToAdd.destination = 'none';
      }
      this.usersService.addUser(userToAdd).subscribe((user: User) => { 
        userToAdd.id = user.id; 
        if (userToAdd.destination !== '' && userToAdd.destination !== 'none' && userToAdd.professor === false) {
          this.schoolService.updatePeopleInterested(this.schoolService.getSchoolByName(userToAdd.destination),userToAdd.id);
        }
      });
      if (this.usersService.getHasBeenRedirected() === 'login' || this.usersService.getHasBeenRedirected() === 'register') {
        this.router.navigate(['']);
      } else if (this.usersService.getHasBeenRedirected() === 'appointments' && this.usersService.getCurrentUser().professor) {
        this.router.navigate(['./appointments/teachers']);
      } else if (this.usersService.getHasBeenRedirected() === 'appointments' && !this.usersService.getCurrentUser().professor) {
        this.router.navigate(['./appointments/students']);
      } else if (this.usersService.getHasBeenRedirected() === 'stats' && this.usersService.getCurrentUser().professor) {
        this.router.navigate(['./foreign-informations/teachers']);
      } else if (this.usersService.getHasBeenRedirected() === 'stats' && !this.usersService.getCurrentUser().professor) {
        this.router.navigate(['./foreign-informations/students']);
      } else if (this.usersService.getHasBeenRedirected() === 'false') {
        this.location.back();
      }
    }
  }
}
