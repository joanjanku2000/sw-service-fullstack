import { Routes } from '@angular/router';
import { AppServiceListComponent } from './components/app-service-list/app-service-list.component';
import { AppServiceDetailsComponent } from './components/app-service-details/app-service-details.component';
import { AppServiceCreateComponent } from './components/app-service-create/app-service-create.component';

const routes: Routes = [
  { path: 'services', component: AppServiceListComponent, data: { prerender: false } },
  {
    path: 'service/:id', component: AppServiceDetailsComponent,
    data: { prerender: false }
  },
  { path: 'create', component: AppServiceCreateComponent, data: { prerender: false } },
  { path: 'update/:id', 
    component: AppServiceCreateComponent, 
    data: { prerender: false } },
  { path: '', redirectTo: 'services', pathMatch: 'full',data: { prerender: false } }
];


export default routes;
