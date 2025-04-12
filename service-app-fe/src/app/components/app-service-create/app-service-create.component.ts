import { Component, OnInit } from '@angular/core';
import { Owner } from '../../models/owner.model';
import { Resource } from '../../models/resource.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from '../shared/header/header.component';
import { AppServiceService } from '../../services/appService.service';

@Component({
  selector: 'app-app-service-create',
  imports: [CommonModule, FormsModule, HeaderComponent],
  templateUrl: './app-service-create.component.html',
  styleUrl: './app-service-create.component.scss'
})
export class AppServiceCreateComponent implements OnInit {
  showResourceModal = false;
  successMessageVisible = false;
  errorMessageVisible = false;
  resources: Resource[] = [];

  ownerForm: Owner = {
    id: '',
    name: '',
    accountNumber: '',
    level: 0
  };

  owners: Owner[] = [];

  resourceId = '';

  constructor(private appServiceService: AppServiceService) { }

  ngOnInit(): void {
    this.resetForm();
  }

  openResourceModal() {
    this.resetForm();
    this.showResourceModal = true;
  }

  closeResourceModal() {
    this.showResourceModal = false;
  }

  addOwner() {
    this.owners.push({ ...this.ownerForm });
    this.ownerForm = { id: '', name: '', accountNumber: '', level: 0 };
  }

  removeOwner(index: number) {
    this.owners.splice(index, 1);
  }

  submitResource() {
    const resource: Resource = {
      owners: [...this.owners]
    };
    this.resources.push(resource);
    this.closeResourceModal();
  }

  submitService() {

    console.log('Submitted Service:', this.resources);

    this.appServiceService.createAppService({ resources: this.resources })

      .subscribe({
        next: (response) => {
          
          this.successMessageVisible = true;

          this.successMessageVisible = true;
          this.resources = [];
          this.resetForm();

          setTimeout(() => {
            this.successMessageVisible = false;
          }, 3000);
        },
        error: (error) => {
          this.successMessageVisible = false;
          this.errorMessageVisible = true
          console.error('API Error:', error.status);
          console.error('Message', error.message);
        }
      }
      )

  }

  removeResource(index: number) {
    this.resources.splice(index, 1);
  }


  resetForm() {
    this.ownerForm = { id: '', name: '', accountNumber: '', level: 0 };
    this.owners = [];
    this.resourceId = '';
  }

}
