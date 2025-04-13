import { Routes } from '@angular/router';
import { AppServiceListComponent } from './components/app-service-list/app-service-list.component';
import { AppServiceDetailsComponent } from './components/app-service-details/app-service-details.component';
import { AppServiceCreateComponent } from './components/app-service-create/app-service-create.component';

const routes: Routes = [
    { path: 'services', component: AppServiceListComponent },
    { path: 'service/:id', component: AppServiceDetailsComponent },
    { path: 'create', component: AppServiceCreateComponent },
    { path: 'update/:id', component:  AppServiceCreateComponent},
    { path: '', redirectTo: 'services', pathMatch: 'full' }
  ];

  export default routes;
