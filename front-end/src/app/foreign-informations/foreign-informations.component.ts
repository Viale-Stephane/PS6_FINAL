import { Component, OnInit } from '@angular/core';
import { ChartOptions, ChartType, ChartDataSets } from 'chart.js';
import { Label } from 'ng2-charts';
import { School } from 'src/models/school';
import {BehaviorSubject, of} from 'rxjs';
import { UsersService } from 'src/services/users/users.service';
import { User } from 'src/models/users';
import { SchoolService } from 'src/services/school/school.service';
import {Router} from '@angular/router';



@Component({
  selector: 'app-foreign-informations',
  templateUrl: './foreign-informations.component.html',
  styleUrls: ['./foreign-informations.component.scss'],
})
export class ForeignInformationsComponent implements OnInit {
  private schoolList: School[] = [];
  private url = 'http://localhost:9428/api/school';
  public schools$: BehaviorSubject<School[]> = new BehaviorSubject(this.schoolList);
  private studentList: User[] = [];

  public barChartOptions: ChartOptions = {
    responsive: true,
    // We use these empty structures as placeholders for dynamic theming.
    scales: { xAxes: [{}], yAxes: [{}] },
  };
  public barChartLabels: Label[] = ['2012', '2013', '2014', '2015', '2016', '2017', '2018'];
  public barChartType: ChartType = 'bar';
  public barChartLegend = true;
  public somme: number[] = [];
  public view1 = true;

  selectedIndex = 0;
  public selectedSchool: School = null;

  constructor(private userService: UsersService, private schoolService: SchoolService, private router: Router) {
    this.schoolService.getSchool();
    this.schoolService.school$.subscribe((schools) => {
      this.schoolList = schools;
      this.selectedSchool = schools[0];
    });
  }

  ngOnInit() {
     if (this.userService.isConnectedAs() === 0) {
      this.router.navigate(['./login']);
    }
  }

  public changeView(): void {
    this.view1 = !this.view1;
  }

  public update(event): void {
    this.selectedIndex = event;
    this.selectedSchool = this.schoolList[this.selectedIndex];
  }

  public article(): any {
    if (this.selectedSchool) {
      return (this.selectedSchool.name == 'Global') ? 'De manière ' : (this.selectedSchool.name == 'Hong Kong') ? 'Pour ' : (this.selectedSchool.name == 'Canada' || this.selectedSchool.name == 'Danemark') ? 'Pour le ' : 'Pour la ';
    }
    return '';
  }

  public getUserById(id: Number): User {
    return this.userService.getUserById(id);
  }

  public moyenne(): string {
    this.somme = [];
    let moy = 0;
    if (this.selectedSchool) {
      for (let index = 0; index < this.selectedSchool.barChartData[0].data.length; index++) {
        const element = this.selectedSchool.barChartData[0].data[index];
        const element2 = this.selectedSchool.barChartData[1].data[index];
        this.somme[index] = element2 / element;
      }
      this.somme.forEach(element => {
        moy = element + moy;
      });
      return ((moy / 7) * 100).toFixed(2);
    }
    return '0';
  }


  // events
  public chartClicked({ event, active }: { event: MouseEvent, active: {}[] }): void {
    console.log(event, active);
  }

  public chartHovered({ event, active }: { event: MouseEvent, active: {}[] }): void {
    console.log(event, active);
  }

  public changeType(): void {
    this.barChartType = this.barChartType === 'bar' ? 'line' : 'bar';
  }

}
