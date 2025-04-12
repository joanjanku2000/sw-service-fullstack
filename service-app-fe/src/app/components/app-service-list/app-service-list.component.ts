import { Component, OnInit } from '@angular/core';
import { AppServiceService } from '../../services/appService.service';
import { Router, RouterModule } from '@angular/router';
import { AppServicePage } from '../../models/servicePage.model';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../shared/header/header.component';

@Component({
  selector: 'app-app-service-list',
  standalone: true,
  imports: [RouterModule, CommonModule, HeaderComponent],
  templateUrl: './app-service-list.component.html',
  styleUrl: './app-service-list.component.scss'
})

export class AppServiceListComponent implements OnInit {
  appServicePage?: AppServicePage;
 
  constructor(private route: Router,
    private appServiceService: AppServiceService) { }


  ngOnInit(): void {
    this.appServiceService.findAll(0).subscribe((data) => (this.appServicePage = data));
  }

  navigateToDetailsPage(id: string): void {
    this.route.navigate(['/service' , id]);
  }

  loadNextPage() {
    if (this.appServicePage?.number! + 1 < this.appServicePage?.totalPages!) {
      const nextPage = this.appServicePage?.number! + 1;
      this.appServiceService.findAll(nextPage).subscribe((data) => (this.appServicePage = data)); // Assuming you have a paginated fetch method
    }
  }
 
  loadPreviousPage() {
    if (this.appServicePage?.number! - 1 >= 0) {
      const nextPage = this.appServicePage?.number! - 1;
      this.appServiceService.findAll(nextPage).subscribe((data) => (this.appServicePage = data)); // Assuming you have a paginated fetch method
    }
  }

}
