import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarnetsComponent } from './carnets.component';

describe('CarnetsComponent', () => {
  let component: CarnetsComponent;
  let fixture: ComponentFixture<CarnetsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CarnetsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CarnetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
