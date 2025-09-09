import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShareInfoRequestComponent } from './share-info-request.component';

describe('ShareInfoRequestComponent', () => {
  let component: ShareInfoRequestComponent;
  let fixture: ComponentFixture<ShareInfoRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ShareInfoRequestComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ShareInfoRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
