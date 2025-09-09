import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReversedChartComponent } from './reversed-chart.component';

describe('ReversedChartComponent', () => {
  let component: ReversedChartComponent;
  let fixture: ComponentFixture<ReversedChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReversedChartComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ReversedChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
