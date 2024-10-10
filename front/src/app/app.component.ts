import { Component } from '@angular/core';
import { Router } from '@angular/router';  // Import du service Router

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'front';

  constructor(public router: Router) { }  // Injection du service Router
}
