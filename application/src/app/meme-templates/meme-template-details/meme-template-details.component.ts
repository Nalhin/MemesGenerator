import { Component, OnInit } from '@angular/core';
import { MemeTemplatesService } from '../meme-templates.service';
import { Observable } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { switchMap } from 'rxjs/operators';
import { TemplateResponseDto } from '../../shared/interfaces/api.interface';

@Component({
  selector: 'app-meme-template-details',
  templateUrl: './meme-template-details.component.html',
})
export class MemeTemplateDetailsComponent implements OnInit {
  memeTemplateDetails$: Observable<TemplateResponseDto>;

  constructor(
    private readonly memeTemplatesService: MemeTemplatesService,
    private readonly activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.memeTemplateDetails$ = this.activatedRoute.paramMap.pipe(
      switchMap((params) => {
        return this.memeTemplatesService.getOneById(Number(params.get('id')));
      }),
    );
  }
}
