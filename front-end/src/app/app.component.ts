import { Component, OnInit } from '@angular/core';
import { User } from 'src/models/users';
import { UsersService } from 'src/services/users/users.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'starter-project';

  public currentUser: User;

  constructor( public userService: UsersService) {
  }

  ngOnInit() {
    this.userService.currentUser$.subscribe((user) => {

      this.currentUser = user;

    });
    this.currentUser = this.userService.getCurrentUser();

  }
}
