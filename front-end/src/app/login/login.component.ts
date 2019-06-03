import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { User } from 'src/models/users';
import { UsersService } from 'src/services/users/users.service';
import { Router } from '@angular/router';
import { Location } from '@angular/common';


interface Alert {
  type: string;
  message: string;
}

const ALERTS: Alert[] = [{
  type: 'success',
  message: 'Connexion réussie ! Vous êtes désormais connecté.\n',
}, {
  type: 'info',
  message: 'This is an info alert',
}, {
  type: 'warning',
  message: 'Vous devez vous connecter pour accéder à cette page',
}, {
  type: 'danger',
  message: 'Connexion refusée ! Mauvais nom d\'utilisateur ou de mot de passe.',
}, {
  type: 'primary',
  message: 'This is a primary alert',
}, {
  type: 'secondary',
  message: 'This is a secondary alert',
}, {
  type: 'light',
  message: 'This is a light alert',
}, {
  type: 'dark',
  message: 'This is a dark alert',
}
];

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  public error: boolean;
  alerts: Alert[];

  public loginForm: FormGroup;
  public USER_LIST: User[];
  public isLoggedIn: Number = 0;
  constructor(public formBuilder: FormBuilder, public usersService: UsersService, private location: Location, private router: Router) {
    this.loginForm = this.formBuilder.group({
      username: [''],
      password: [''],
      professor: [''],
    });
    this.resetAlert();
    this.USER_LIST = this.usersService.getUsers();
  }



  ngOnInit() {
    this.usersService.users$.subscribe((user) => {
      this.USER_LIST = user;
    });

    this.usersService.getUsersFromBack();
    this.USER_LIST = this.usersService.getUsers();
    if (this.usersService.getHasBeenRedirected() !== 'false') {
       this.error = true;
    }
  }

  goBack() {
    this.location.back();
  }

  public verifyUser() {
    const userToVerify: User = this.loginForm.getRawValue() as User;
    for (let i = 0; i < this.USER_LIST.length; i++) {
      if (userToVerify.username === this.USER_LIST[i].username && userToVerify.password === this.USER_LIST[i].password) {
        this.isLoggedIn = 1;
        this.usersService.setCurrentUsers(this.USER_LIST[i]);
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
        break;
      } else if ((i + 1) === this.USER_LIST.length) {
        this.isLoggedIn = -1;
      }
    }
}

  closeAlert(alert: Alert) {
    this.alerts.splice(this.alerts.indexOf(alert), 1);
  }

  resetAlert() {
    this.alerts = Array.from(ALERTS);
  }
}
