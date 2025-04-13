import { Component, OnInit } from '@angular/core';
import { AppService } from '../../models/service.model';
import { AppServiceService } from '../../services/appService.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../shared/header/header.component';
import { error } from 'console';

@Component({
  selector: 'app-app-service-details',
  standalone: true,
  imports: [RouterModule, CommonModule, HeaderComponent],
  templateUrl: './app-service-details.component.html',
  styleUrl: './app-service-details.component.scss',

})
export class AppServiceDetailsComponent implements OnInit {

  appService?: AppService;
  expandedResources: boolean[] = [];
  successMessageVisible = false;
  errorMessageVisible = false;
  notFoundErrorMessage = false;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private appServiceService: AppServiceService) { }

  ngOnInit(): void {

    const id: string = this.route.snapshot.paramMap.get('id')!;

    this.appServiceService.findById(id)
      .subscribe(
        {
          next: (data) => (this.appService = data),
          error: (error) => {
            this.handleNotFoundError(error);
          }
        }
      );

  }

  toggleResource(index: number): void {
    this.expandedResources[index] = !this.expandedResources[index];
  }

  onUpdateService(id?: string): void {
    if (!id) {
      return;
    }
    this.router.navigate(['/update', id]);
  }

  onDeleteService(id?: string): void {
    if (!id) {
      return;
    }
    this.appServiceService.delete(id)
      .subscribe(
        {
          next: (response) => {
            this.handleSuccess();
          },
          error: (error) => {
            this.handleError(error);
          }
        }
      )
  }

  private handleSuccess() {
    this.successMessageVisible = true;
    setTimeout(() => {

      this.router.navigate(['/services']);
    }, 3000);
  }

  private handleError(error: any) {
    this.errorMessageVisible = true;
    this.logError(error);
    
    setTimeout(() => {
      this.errorMessageVisible = false;
    }, 3000);

    this.logError(error);
  }


  private logError(error: any) {
    console.error('API Error:', error.status);
    console.error('Message', error.message);
  }

  private handleNotFoundError(error: any) {
    this.notFoundErrorMessage = true;
    this.logError(error);

    setTimeout(() => {
      this.notFoundErrorMessage = false;
      this.router.navigate(['/services']);
    }, 3000);
    
  }
}
