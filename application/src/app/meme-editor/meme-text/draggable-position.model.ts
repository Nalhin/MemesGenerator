export class DraggablePosition {
  private _x: number;
  private _y: number;

  private _xBound: Bound;
  private _yBound: Bound;

  setXBound(min: number, max: number) {
    this._xBound = new Bound({ min, max });
  }

  setYBound(min: number, max: number) {
    this._yBound = new Bound({ min, max });
  }

  set x(x) {
    this._x = x;
  }

  get x() {
    return this._xBound.nonExtendingValue(this._x);
  }

  set y(y) {
    this._y = y;
  }

  get y() {
    return this._yBound.nonExtendingValue(this._y);
  }

  constructor(partial?: Partial<DraggablePosition>) {
    Object.assign(this, partial);
  }
}

class Bound {
  min: number;
  max: number;

  nonExtendingValue(value: number) {
    const min = Math.max(value, this.min);
    return Math.min(min, this.max);
  }

  constructor(partial?: Partial<Bound>) {
    Object.assign(this, partial);
  }
}
