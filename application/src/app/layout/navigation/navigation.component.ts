import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../auth/auth.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
})
export class NavigationComponent implements OnInit {
  isAuthenticated$: Observable<boolean>;

  constructor(private readonly authService: AuthService) {}

  ngOnInit(): void {
    this.isAuthenticated$ = this.authService.isAuthenticated();
  }
}
