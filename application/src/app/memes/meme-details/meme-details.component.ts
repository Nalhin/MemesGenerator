import { Component, OnInit } from '@angular/core';
import { MemesService } from '../memes.service';
import { Observable } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { switchMap } from 'rxjs/operators';
import { MemeResponseDto } from '../../shared/interfaces/api.interface';

@Component({
  selector: 'app-meme-details',
  templateUrl: './meme-details.component.html',
})
export class MemeDetailsComponent implements OnInit {
  meme$: Observable<MemeResponseDto>;

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
