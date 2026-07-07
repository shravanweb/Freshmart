import { AxisDirection } from "./AxisDirection";

export abstract class ScrollActivity {
  delegate: ScrollActivityDelegate;
  isScrolling: boolean = false;
  shouldIgnorePointer: number = 0;
  velocity: number = 0;
  constructor(delegate: ScrollActivityDelegate) {
    this.delegate = delegate;
  }
}
export abstract class ScrollActivityDelegate {
  axisDirection: AxisDirection;
}
export abstract class ScrollContext {
  axisDirection: AxisDirection;
  vsync: TickerProvider;
}
export abstract class TickerProvider {
  TickerProvider() {
    //TODO
  }
}
