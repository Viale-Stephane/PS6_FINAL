import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { UsersService } from 'src/services/users/users.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header-online',
  templateUrl: './header-online.component.html',
  styleUrls: ['./header-online.component.scss']
})
export class HeaderOnlineComponent implements OnInit {

  collapsed = true;
  toggleCollapsed(): void {
    this.collapsed = !this.collapsed;
  }

  constructor(private userService: UsersService, private router: Router) {
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

  selectLogout() {
    this.userService.logOut();
    const courses = /courses/;
    if (this.router.url.search(courses) === -1) {
      this.router.navigate(['./']);
    }
  }



}
