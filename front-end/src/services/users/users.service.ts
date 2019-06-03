import { USERS_MOCKED } from '../../mocks/users.mock';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from 'src/models/users';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})

export class UsersService {
    private usersList: User[] = USERS_MOCKED;
    private currentUser: User = null;

    private urlLogin = 'http://localhost:9428/api/login';
    private urlRegister = 'http://localhost:9428/api/register';

    private HasBeenRedirected = 'false';


    public users$: BehaviorSubject<User[]> = new BehaviorSubject(this.usersList);

    public currentUser$: BehaviorSubject<User> = new BehaviorSubject(this.currentUser);

    constructor(private http: HttpClient) {
        this.http.get<User[]>(this.urlLogin).subscribe((user) => {
            this.usersList = user;
            this.users$.next(user);
        });
    }

    public getUsersFromBack() {
        this.http.get<User[]>(this.urlLogin).subscribe((user) => {
            this.usersList = user;
            this.users$.next(user);
        });
    }
    public getUsers() {
        /*this.http.get<User[]>(this.urlLogin).subscribe((user) => {
            this.usersList = user;
            this.users$.next(user);
        });*/
        return this.usersList;
    }

    public getUserById(id: Number) {
        for (let i = 0; i < this.usersList.length; i += 1) {
            if (this.usersList[i].id === id) {
                return this.usersList[i];
            }
        }
        return null;
    }

    public getCurrentUser() {
        return this.currentUser;
    }

    public setCurrentUsers(newUser: User) {
        this.currentUser = newUser;
        this.currentUser$.next(newUser);
    }

    public addUser(newUser: User): Observable<User> {
        this.setCurrentUsers(newUser);
        return this.http.post(this.urlRegister, newUser);
    }

    public getPhoneNumber(user: User){
        return user.phoneNumber;
    }

    public logOut() {
        this.HasBeenRedirected = 'false';
        this.currentUser = null;
        this.currentUser$.next(null);
    }

    public isConnectedAs() {// 0 = non connecté  1 = élève 2 = prof
        if (this.currentUser === null) {
            return 0;
        } else if (this.currentUser.professor === true) {
            return 2;
        } else {
            return 1;
        }
    }

    public redirectingToLogin(redirection: string) {
        this.HasBeenRedirected = redirection;
    }

    public leavingLoginWhileNotConnected() {
        this.HasBeenRedirected = 'false';
    }

    public getHasBeenRedirected() {
        return this.HasBeenRedirected;
    }
}
