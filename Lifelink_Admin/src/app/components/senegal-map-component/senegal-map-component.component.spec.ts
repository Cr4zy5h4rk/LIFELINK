import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SenegalMapComponentComponent } from './senegal-map-component.component';

describe('SenegalMapComponentComponent', () => {
  let component: SenegalMapComponentComponent;
  let fixture: ComponentFixture<SenegalMapComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SenegalMapComponentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SenegalMapComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
