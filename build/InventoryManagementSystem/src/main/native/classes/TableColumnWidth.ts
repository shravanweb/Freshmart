export class TableColumnWidth {
  private _id: number = 0;
  private _columnWidth: string = "";
  public constructor(d3eParams?: Partial<{ columnWidth: string }>) {
    if (d3eParams?.columnWidth) {
      this.setColumnWidth(d3eParams?.columnWidth);
    }
  }
  public get id(): number {
    return this._id;
  }
  public set id(id: number) {
    this._id = id;
  }
  public get d3eType(): string {
    return "TableColumnWidth";
  }
  public clear(): void {}

  public get columnWidth(): string {
    return this._columnWidth;
  }
  public setColumnWidth(val: string): void {
    let isValChanged: boolean = this._columnWidth !== val;

    if (!isValChanged) {
      return;
    }

    this._columnWidth = val;
  }
  public equals(other: any): boolean {
    return (
      this === other ||
      (other instanceof TableColumnWidth &&
        this._columnWidth === other._columnWidth)
    );
  }
  public get hashCode(): number {
    return this._columnWidth?.hashCode ?? 0;
  }
}
