import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BloodStatsComponent } from './blood-stats.component';

describe('BloodStatsComponent', () => {
  let component: BloodStatsComponent;
  let fixture: ComponentFixture<BloodStatsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BloodStatsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BloodStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
