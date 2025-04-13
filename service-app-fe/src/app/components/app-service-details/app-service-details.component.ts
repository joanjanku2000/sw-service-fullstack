import { Component, OnInit } from '@angular/core';
import { AppService } from '../../models/service.model';
import { AppServiceService } from '../../services/appService.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../shared/header/header.component';

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

  constructor(private route: ActivatedRoute,
    private router: Router,
    private appServiceService: AppServiceService) { }

  ngOnInit(): void {

    const id: string = this.route.snapshot.paramMap.get('id')!;

    this.appServiceService.findById(id)
      .subscribe((data) => (this.appService = data));

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
}
