import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppServiceDetailsComponent } from './app-service-details.component';

describe('AppServiceDetailsComponent', () => {
  let component: AppServiceDetailsComponent;
  let fixture: ComponentFixture<AppServiceDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppServiceDetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppServiceDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
