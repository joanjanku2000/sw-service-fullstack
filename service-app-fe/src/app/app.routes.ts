import { Routes } from '@angular/router';
import { AppServiceListComponent } from './components/app-service-list/app-service-list.component';
import { AppServiceDetailsComponent } from './components/app-service-details/app-service-details.component';
import e from 'express';

const routes: Routes = [
    { path: 'services', component: AppServiceListComponent },
    { path: 'service/:id', component: AppServiceDetailsComponent },
    { path: 'create', component: AppServiceListComponent },
    { path: 'edit/:id', component: AppServiceListComponent },
    { path: '', redirectTo: 'services', pathMatch: 'full' }
  ];

  export default routes;
