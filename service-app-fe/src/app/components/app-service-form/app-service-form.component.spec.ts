import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppServiceFormComponent } from './app-service-form.component';

describe('AppServiceFormComponent', () => {
  let component: AppServiceFormComponent;
  let fixture: ComponentFixture<AppServiceFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppServiceFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppServiceFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
