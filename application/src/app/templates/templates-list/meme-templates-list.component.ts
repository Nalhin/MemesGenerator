import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { TemplatesService } from '../templates.service';
import { PageTemplateResponseDto } from '../../shared/interfaces/api.interface';

@Component({
  selector: 'app-meme-templates-list',
  templateUrl: './meme-templates-list.component.html',
})
export class MemeTemplatesListComponent implements OnInit {
  memeTemplates$: Observable<PageTemplateResponseDto>;
  currentPage = 0;

  constructor(private readonly memeTemplatesService: TemplatesService) {}

  ngOnInit(): void {
    this.memeTemplates$ = this.memeTemplatesService.getAll(this.currentPage);
  }
}
