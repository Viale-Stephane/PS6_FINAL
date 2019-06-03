import { School } from '../../models/school';
import { User } from '../../models/users';
import { SPE_MOCK } from '../../mocks/specialties.mock';
import {BehaviorSubject} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {computeStyle} from '@angular/animations/browser/src/util';
import { UsersService } from '../users/users.service';

@Injectable({
    providedIn: 'root'
})

export class SchoolService {
    public schoolList: School[] = [];
    
    private url = 'http://localhost:9428/api/school';

    public school$: BehaviorSubject<School[]> = new BehaviorSubject(this.schoolList);

    constructor(private http: HttpClient) {
        this.getSchool();
    }

    public getSchool() {
        this.http.get<School[]>(this.url).subscribe((school) => {
            this.schoolList = school;
            this.school$.next(school);
        });
    }

    public updatePeopleInterested(school: School, userId: number) {
        
        school.peopleInterested.push(userId);
        const urlWithId = this.url + '/' + school.id;
        this.http.put<School>(urlWithId, school).subscribe(() => this.getSchool());
    }

    public getSchoolByName(schoolName: String) {
        for (let index = 0; index < this.schoolList.length; index++) {
            const school = this.schoolList[index];
            if (school.name === schoolName) {
                return school;
            }
        }
        return null;
    }

}
