import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyDynamicChartComponent } from './my-dynamic-chart.component';

describe('MyDynamicChartComponent', () => {
  let component: MyDynamicChartComponent;
  let fixture: ComponentFixture<MyDynamicChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyDynamicChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyDynamicChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
