import { Component, OnInit } from '@angular/core';
import { UsersService } from 'src/services/users/users.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header-offline',
  templateUrl: './header-offline.component.html',
  styleUrls: ['./header-offline.component.scss']
})
export class HeaderOfflineComponent implements OnInit {

  collapsed = true;
  toggleCollapsed(): void {
    this.collapsed = !this.collapsed;
  }

  constructor(private userService: UsersService, private router: Router) { }

  ngOnInit() {
  }

  public leavingLoginWhileNotConnected() {
    this.userService.leavingLoginWhileNotConnected();
  }

  selectAppointment() {
    if (this.userService.isConnectedAs() === 0) { // not connected
      this.userService.redirectingToLogin('appointments');
      this.router.navigate(['./login']);
    } else if (this.userService.isConnectedAs() === 1 ) { // student
      this.router.navigate(['./appointments']);
    } else {// sensei
      this.router.navigate(['./appointments']);
    }
}

  selectStats() {
    if (this.userService.isConnectedAs() === 0) { // not connected
      this.userService.redirectingToLogin('stats');
      this.router.navigate(['./login']);
    } else if (this.userService.isConnectedAs() === 1 ) { // student
        this.router.navigate(['./foreign-informations']);
    } else {// sensei
        this.router.navigate(['./foreign-informations']);
    }
  }

  selectLogin() {

    if (this.router.url.search('courses') === -1) {
      this.userService.redirectingToLogin('login');
    }
    this.router.navigate(['./login']);
  }

  selectRegister() {
    if (this.router.url.search('courses') === -1) {
      this.userService.redirectingToLogin('register');
    }
    this.router.navigate(['./register']);
  }
}
