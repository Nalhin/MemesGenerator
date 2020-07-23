import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { LoadingService } from '../services/loading/loading.service';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss'],
})
export class LayoutComponent implements OnInit {
  isLoading$: Observable<boolean>;

  constructor(private readonly requestLoadingService: LoadingService) {}

  ngOnInit(): void {
    this.isLoading$ = this.requestLoadingService.isLoading();
  }
}
