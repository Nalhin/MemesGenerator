import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { MemeTemplatesService } from '../meme-templates.service';

@Component({
  selector: 'app-meme-templates-list',
  templateUrl: './meme-templates-list.component.html',
})
export class MemeTemplatesListComponent implements OnInit {
  memeTemplates$: Observable<Api.PageTemplateResponseDto>;
  currentPage = 0;

  constructor(private readonly memeTemplatesService: MemeTemplatesService) {}

  ngOnInit(): void {
    this.memeTemplates$ = this.memeTemplatesService.getAll(this.currentPage);
  }
}
