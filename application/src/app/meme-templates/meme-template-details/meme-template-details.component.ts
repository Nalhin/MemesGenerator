import { Component, OnInit } from '@angular/core';
import { MemeTemplatesService } from '../meme-templates.service';
import { Observable } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-meme-template-details',
  templateUrl: './meme-template-details.component.html',
})
export class MemeTemplateDetailsComponent implements OnInit {
  memeTemplateDetails$: Observable<Api.TemplateResponseDto>;

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
