import { Output, EventEmitter, OnInit, Component } from '@angular/core';
import { Router } from '@angular/router';
import { UsersService } from 'src/services/users/users.service';


@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
  })

  export class HomeComponent implements OnInit {
      constructor(private router: Router, private userService: UsersService) {
      }

      ngOnInit() {
      }

      selectAppointment() {
          if (this.userService.isConnectedAs() === 0) { // not connected
            this.userService.redirectingToLogin('appointments');
            this.router.navigate(['./login']);
          } else if (this.userService.isConnectedAs() === 1 ) { // student
            this.router.navigate(['./appointments/students']);
          } else {// sensei
            this.router.navigate(['./appointments/teachers']);
          }
      }

      selectStats() {
        if (this.userService.isConnectedAs() === 0) { // not connected
          this.userService.redirectingToLogin('stats');
          this.router.navigate(['./login']);
        } else if (this.userService.isConnectedAs() === 1 ) { // student
            this.router.navigate(['./foreign-informations/students']);
        } else {// sensei
            this.router.navigate(['./foreign-informations/teachers']);
        }
      }
  }
