import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { RequestLoadingService } from '../services/request-loading.service';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss'],
})
export class LayoutComponent implements OnInit {
  isLoading$: Observable<boolean>;

  constructor(private readonly requestLoadingService: RequestLoadingService) {}

  ngOnInit(): void {
    this.isLoading$ = this.requestLoadingService.isLoading();
  }
}
