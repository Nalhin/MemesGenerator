import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Subscription } from 'rxjs';
import { BreakpointObserver } from '@angular/cdk/layout';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss'],
})
export class NavigationComponent implements OnInit, OnDestroy {
  isAuthenticated: boolean;
  isMobile: boolean;
  subscriptions: Subscription[] = [];

  constructor(
    private readonly authService: AuthService,
    private readonly breakpointObserver: BreakpointObserver,
  ) {}

  ngOnInit(): void {
    this.subscriptions.push(
      this.authService.isAuthenticated().subscribe((val) => {
        this.isAuthenticated = val;
      }),
    );

    this.subscriptions.push(
      this.breakpointObserver
        .observe(['(max-width: 599px)'])
        .subscribe((result) => {
          this.isMobile = result.matches;
        }),
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }
}
