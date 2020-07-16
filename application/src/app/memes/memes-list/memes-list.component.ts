import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { MemesService } from '../memes.service';
import { PageMemeResponseDto } from '../../shared/interfaces/api.interface';

@Component({
  selector: 'app-memes-list',
  templateUrl: './memes-list.component.html',
})
export class MemesListComponent implements OnInit {
  memes$: Observable<PageMemeResponseDto>;
  currentPage = 0;

  constructor(private readonly memesService: MemesService) {}

  ngOnInit(): void {
    this.memes$ = this.memesService.getAll(this.currentPage);
  }
}
