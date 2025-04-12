import { Component, OnInit } from '@angular/core';
import { AppService } from '../../models/service.model';
import { AppServiceService } from '../../services/appService.service';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-app-service-details',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './app-service-details.component.html',
  styleUrl: './app-service-details.component.scss',

})
export class AppServiceDetailsComponent implements OnInit { 
  
  appService?: AppService;
  expandedResources: boolean[] = [];

  constructor(private route: ActivatedRoute,
    private appServiceService: AppServiceService) { }

  ngOnInit(): void {
    console.log("INIT")
    const id: string = this.route.snapshot.paramMap.get('id')!;
    console.log("ID: " + id)
    this.appServiceService.findById(id).subscribe((data) => (this.appService = data));
   
    console.log("AppService: " + JSON.stringify(this.appService))
  }

  toggleResource(index: number): void {
    this.expandedResources[index] = !this.expandedResources[index];
  }
}
