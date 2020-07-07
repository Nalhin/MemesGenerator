import {
  AfterViewInit,
  Component,
  ElementRef,
  Input,
  OnChanges,
  ViewChild,
} from '@angular/core';
import { MemeText } from '../meme-text/meme-text.model';
import { MemesService } from '../../memes/memes.service';

@Component({
  selector: 'app-meme-canvas',
  template: ` <canvas #canvas width="400" height="400"></canvas>
    <button class="absolute top-0" mat-button (click)="saveImage()">
      Save
    </button>`,
})
export class MemeCanvasComponent implements AfterViewInit, OnChanges {
  @ViewChild('canvas')
  canvas: ElementRef<HTMLCanvasElement>;
  context: CanvasRenderingContext2D;

  @Input()
  memeTexts: MemeText[];

  @Input()
  memeTemplateDetails: Api.TemplateResponseDto;

  constructor(private readonly memesService: MemesService) {}

  ngAfterViewInit(): void {
    this.context = this.canvas.nativeElement.getContext('2d');
  }

  ngOnChanges(changes) {
    if (changes.memeTemplateDetails.currentValue?.url) {
      this.loadImage();
    }
  }

  loadImage(): void {
    const image = new Image();
    image.src = this.memeTemplateDetails.url;
    image.crossOrigin = 'Anonymous';
    image.onload = () => {
      this.context.drawImage(
        image,
        0,
        0,
        this.canvas.nativeElement.width,
        this.canvas.nativeElement.height,
      );
    };
  }

  saveImage(): void {
    this.context.font = '30px Impact';
    for (const memeText of this.memeTexts) {
      this.context.fillText(
        memeText.text,
        memeText.position.x,
        memeText.position.y,
      );
    }
    this.canvas.nativeElement.toBlob((blob) => {
      this.memesService
        .save(blob, { templateId: this.memeTemplateDetails.id })
        .subscribe();
    });
  }
}
