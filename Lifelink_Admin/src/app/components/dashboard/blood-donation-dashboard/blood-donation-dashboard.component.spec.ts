import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BloodDonationDashboardComponent } from './blood-donation-dashboard.component';

describe('BloodDonationDashboardComponent', () => {
  let component: BloodDonationDashboardComponent;
  let fixture: ComponentFixture<BloodDonationDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BloodDonationDashboardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BloodDonationDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
