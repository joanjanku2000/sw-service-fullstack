import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppServiceCreateComponent } from './app-service-create.component';

describe('AppServiceCreateComponent', () => {
  let component: AppServiceCreateComponent;
  let fixture: ComponentFixture<AppServiceCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppServiceCreateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppServiceCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
