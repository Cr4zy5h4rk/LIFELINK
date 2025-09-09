import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicEmergenciesComponent } from './public-emergencies.component';

describe('PublicEmergenciesComponent', () => {
  let component: PublicEmergenciesComponent;
  let fixture: ComponentFixture<PublicEmergenciesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PublicEmergenciesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PublicEmergenciesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
