export class MemeText {
  id: number;
  position: {
    x: number;
    y: number;
  };
  size: Size;
  text: string;

  constructor(partial?: Partial<MemeText>) {
    Object.assign(this, partial);
  }
}

export class Size {
  private _width: number;
  private _height: number;

  set height(height) {
    this._height = Math.min(height, 100);
  }

  get height() {
    return this._height;
  }

  set width(width) {
    this._width = Math.min(width, 100);
  }

  get width() {
    return this._width;
  }

  constructor(partial?: Partial<Size>) {
    Object.assign(this, partial);
  }
}
