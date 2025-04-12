import { Routes } from '@angular/router';
import { AppServiceListComponent } from './components/app-service-list/app-service-list.component';
import { AppServiceDetailsComponent } from './components/app-service-details/app-service-details.component';

const routes: Routes = [
    { path: 'services', component: AppServiceListComponent },
    { path: 'service/:id', component: AppServiceDetailsComponent },
    { path: 'create', component: AppServiceListComponent },
    { path: 'update/:id', component: AppServiceListComponent },
    { path: '', redirectTo: 'services', pathMatch: 'full' }
  ];

  export default routes;
