export class BoxConstraints {
  minWidth: number;
  maxWidth: number;
  minHeight: number;
  maxHeight: number;
  constructor(props: {
    minWidth?: number;
    maxWidth?: number;
    minHeight?: number;
    maxHeight?: number;
  }) {
    this.minWidth = props.minWidth;
    this.maxWidth = props.maxWidth;
    this.minHeight = props.minHeight;
    this.maxHeight = props.maxHeight;
  }
}
