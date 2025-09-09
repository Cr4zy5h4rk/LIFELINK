import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticlesDashboardComponent } from './articles-dashboard.component';

describe('ArticlesDashboardComponent', () => {
  let component: ArticlesDashboardComponent;
  let fixture: ComponentFixture<ArticlesDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ArticlesDashboardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ArticlesDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
