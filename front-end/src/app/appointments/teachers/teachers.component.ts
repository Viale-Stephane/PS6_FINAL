import { Component, OnInit } from '@angular/core';
import { UsersService } from 'src/services/users/users.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-teachers',
  templateUrl: './teachers.component.html',
  styleUrls: ['./teachers.component.scss']
})
export class TeachersComponent implements OnInit {

  constructor(private userService: UsersService, private router: Router) { }

  ngOnInit() {
    if (this.userService.isConnectedAs() === 1) {
      this.router.navigate(['./appointments/students']);
    } else if (this.userService.isConnectedAs() === 0) {
      this.router.navigate(['./login']);
    }
  }

}
