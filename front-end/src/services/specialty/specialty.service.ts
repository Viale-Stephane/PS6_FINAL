import { Specialty } from '../../models/specialty';
import { SPE_MOCK } from '../../mocks/specialties.mock';
import {BehaviorSubject} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {computeStyle} from '@angular/animations/browser/src/util';
import {Course} from '../../models/course';

@Injectable({
    providedIn: 'root'
})

export class SpecialtyService {
    public specialtyList: Specialty[] = [];

    private url = 'http://localhost:9428/api/specialties';

    public specialty$: BehaviorSubject<Specialty[]> = new BehaviorSubject(this.specialtyList);

    constructor(private http: HttpClient) {
        this.getSpecialties();
    }
    public getSpecialties() {
        this.http.get<Specialty[]>(this.url).subscribe((specialties) => {
            this.specialtyList = specialties;
            this.specialty$.next(specialties);
            this.specialty$.subscribe((specialtiesbis) => this.specialtyList = specialtiesbis);
        });
    }
    public getSpecialtyByAbr(name) {
        this.getSpecialties();
        for (const specialty of this.specialtyList) {
            if (specialty.abr === name) {
                return specialty;
            }
        }
    }
    addSpecialty(specialty: Specialty) {
        return this.http.post(this.url, specialty);
    }
    deleteSpecialty(specialty: Specialty) {
        this.http.delete<Specialty>(this.url + '/' + specialty.id).subscribe(() => this.getSpecialties());
        this.specialty$.next(this.specialtyList);
    }
    updateSpecialty(specialty: Specialty) {
        const urlWithId = this.url + '/' + specialty.id;
        this.http.put<Specialty>(urlWithId, specialty).subscribe(() => this.getSpecialties());
    }
}
