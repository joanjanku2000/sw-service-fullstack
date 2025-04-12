import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AppServiceDetailsComponent } from './components/app-service-details/app-service-details.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,AppServiceDetailsComponent, AppServiceDetailsComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'frontend-app';
}
