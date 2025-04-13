import { Component, OnInit } from '@angular/core';
import { Owner } from '../../models/owner.model';
import { Resource } from '../../models/resource.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from '../shared/header/header.component';
import { AppServiceService } from '../../services/appService.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-app-service-create',
  imports: [CommonModule, FormsModule, HeaderComponent],
  templateUrl: './app-service-create.component.html',
  styleUrl: './app-service-create.component.scss'
})
export class AppServiceCreateComponent implements OnInit {

  resourceId = '';
  serviceId = '';
  appServiceVersion?: number = -1;

  showResourceModal = false;
  showUpdateOwnersModal = false;
  resourceBeingUpdated: any = null;
  updateResourceIndex = -1;

  successMessageVisible = false;
  errorMessageVisible = false;

  newUpdatedOwner = {
    name: '',
    accountNumber: '',
    level: ''
  };

  ownerForm: Owner = {
    id: '',
    name: '',
    accountNumber: '',
    level: 0
  };

  resources: Resource[] = [];
  owners: Owner[] = [];


  constructor(private appServiceService: AppServiceService, private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    //update
    if (this.activatedRoute.snapshot.params['id']) {
      this.serviceId = this.activatedRoute.snapshot.params['id'];
      this.appServiceService.findById(this.serviceId).subscribe((data) => {
        this.resources = data.resources;
        this.appServiceVersion = data.version;
      });
    } else { // create
      this.resetForm();
    }

  }

  submitService() {
    if (this.serviceId && this.serviceId !== '') {
      this.appServiceService.updateAppService(this.serviceId,
        {
          version: this.appServiceVersion,
          resources: this.resources
        }
      )
        .subscribe({
          next: (response) => {
            this.handleSubmitSuccess();
          },
          error: (error) => {
            this.handleSubmitError(error);
          }
        });
    } else {
      this.appServiceService.createAppService({ resources: this.resources })

        .subscribe({
          next: (response) => {
            this.handleSubmitSuccess();
          },
          error: (error) => {
            this.handleSubmitError(error);
          }
        }
        );
    }
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

  openUpdateOwnersModal(index: number) {
    this.updateResourceIndex = index;
    this.resourceBeingUpdated = {
      ...this.resources[index],
      owners: this.resources[index].owners.map(owner => ({ ...owner }))
    };
    this.newUpdatedOwner = { name: '', accountNumber: '', level: '' };
    this.showUpdateOwnersModal = true;
  }

  closeUpdateOwnersModal() {
    this.showUpdateOwnersModal = false;
    this.resourceBeingUpdated = null;
    this.updateResourceIndex = -1;
  }

  addOwnerToUpdate() {
    if (
      this.newUpdatedOwner.name &&
      this.newUpdatedOwner.accountNumber &&
      this.newUpdatedOwner.level
    ) {
      this.resourceBeingUpdated.owners.push({ ...this.newUpdatedOwner });
      this.newUpdatedOwner = { name: '', accountNumber: '', level: '' };
    }
  }

  removeOwnerFromUpdate(index: number) {
    this.resourceBeingUpdated.owners.splice(index, 1);
  }

  saveUpdatedOwners() {
    if (this.updateResourceIndex > -1) {
      this.resources[this.updateResourceIndex].owners = [
        ...this.resourceBeingUpdated.owners
      ];
    }
    this.closeUpdateOwnersModal();
  }


  removeResource(index: number) {
    this.resources.splice(index, 1);
  }


  resetForm() {
    this.ownerForm = { id: '', name: '', accountNumber: '', level: 0 };
    this.owners = [];
    this.resourceId = '';
  }

  private handleSubmitError(error: any) {
    this.successMessageVisible = false;
    this.errorMessageVisible = true;
    setTimeout(() => {
      this.errorMessageVisible = false;
    }, 3000);
    console.error('API Error:', error.status);
    console.error('Message', error.message);
  }

  private handleSubmitSuccess() {
    this.successMessageVisible = true;
    this.resources = [];
    this.resetForm();

    setTimeout(() => {
      this.successMessageVisible = false;
    }, 3000);
  }

}
