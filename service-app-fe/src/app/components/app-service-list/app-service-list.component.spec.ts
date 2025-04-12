import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppServiceListComponent } from './app-service-list.component';

describe('AppServiceListComponent', () => {
  let component: AppServiceListComponent;
  let fixture: ComponentFixture<AppServiceListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppServiceListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppServiceListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
