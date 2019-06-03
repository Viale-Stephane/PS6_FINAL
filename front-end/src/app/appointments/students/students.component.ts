import { Component, OnInit } from '@angular/core';
import { UsersService } from 'src/services/users/users.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.scss']
})
export class StudentsComponent implements OnInit {

  constructor(private userService: UsersService, private router: Router) { }

  ngOnInit() {
    if (this.userService.isConnectedAs() === 2) {
      this.router.navigate(['./appointments/teachers']);
    } else if (this.userService.isConnectedAs() === 0) {
      this.router.navigate(['./login']);
    }
  }

}
