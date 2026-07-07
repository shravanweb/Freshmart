import ChangeNotifier from "./ChangeNotifier";
import { ScrollPosition } from "./ScrollPosition";
export class CustomScrollPosition extends ScrollPosition {
  constructor(private scrollableElement: HTMLElement) {
    super(scrollableElement); // Call the parent constructor
  }
  getScrollableElement(): HTMLElement {
    return this.scrollableElement;
  }
}

export class ScrollController extends ChangeNotifier {
  debugLabel: string = "";
  hasClients: boolean = false;
  initialScrollOffset: number = 0;
  keepScrollOffset: boolean = true;
  offset: number = 0;
  position: ScrollPosition | null = null;
  positions: Array<ScrollPosition> = [];

  constructor(param?: Partial<ScrollController>) {
    super();
    Object.assign(this, param);
  }

  setScrollableElement(element: HTMLElement) {
    this.position = new CustomScrollPosition(element);
    this.position.pixels = this.initialScrollOffset;
  }

  animateToBottom() {
    if (this.position) {
      const scrollableElement = this.position.getScrollableElement();
      if (scrollableElement) {
        const maxScrollTop =
          scrollableElement.scrollHeight - scrollableElement.clientHeight;
        this.smoothScroll(
          scrollableElement,
          scrollableElement.scrollTop,
          maxScrollTop
        );
      }
    }
  }

  animateToTop() {
    if (this.position) {
      const scrollableElement = this.position.getScrollableElement();
      if (scrollableElement) {
        this.smoothScroll(scrollableElement, scrollableElement.scrollTop, 0);
      }
    }
  }

  private smoothScroll(
    element: HTMLElement,
    start: number,
    target: number,
    duration: number = 300
  ) {
    const distance = target - start;
    const startTime = performance.now();

    const animate = (currentTime: number) => {
      const elapsedTime = currentTime - startTime;
      const progress = Math.min(elapsedTime / duration, 1);
      element.scrollTop = start + distance * this.easeInOutQuad(progress);

      if (progress < 1) {
        requestAnimationFrame(animate);
      }
    };

    requestAnimationFrame(animate);
  }

  private easeInOutQuad(t: number) {
    return t < 0.5 ? 2 * t * t : -1 + (4 - 2 * t) * t;
  }

  setScrollPosition(offset: number) {
    this.offset = offset;
    if (this.position) {
      this.position.pixels = offset;
    }
    this.notifyListeners();
  }
}
