import { Component, OnInit } from '@angular/core';
import { MemesService } from '../memes.service';
import { Observable } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-meme-details',
  templateUrl: './meme-details.component.html',
})
export class MemeDetailsComponent implements OnInit {
  meme$: Observable<Api.MemeResponseDto>;

  constructor(
    private readonly memesService: MemesService,
    private readonly activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.meme$ = this.activatedRoute.paramMap.pipe(
      switchMap((params) => {
        return this.memesService.getOneById(Number(params.get('id')));
      }),
    );
  }
}
