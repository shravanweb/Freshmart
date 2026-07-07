import * as ui from "..";
import Timer from "../../utils/Timer";
import Duration from "../../core/Duration";

type LayoutCallback = (
  bounds: ui.Rect,
  globalPos: ui.Offset,
  parentWidth: number
) => void;

function prepareBox(
  e: HTMLElement
): { renderBox: ui.RenderBox; parentWidth: number } | null {
  if (!e) {
    return null;
  }
  let rect = e.getBoundingClientRect();
  let parentRect = e.parentElement?.getBoundingClientRect();
  let parentWidth = parentRect ? parentRect.width : 0;
  let renderBox = new ui.RenderBox({
    size: new ui.Size({ width: rect.width, height: rect.height }),
    position: new ui.Offset({ dx: rect.left, dy: rect.top }),
  });
  return { renderBox, parentWidth };
}

export default function LayoutAware(
  onBoundsChange?: LayoutCallback,
  d3eRef?: (e: HTMLElement) => void
) {
  let bounds: ui.Rect;
  return (e: HTMLElement) => {
    if (d3eRef) {
      d3eRef(e);
    }
    if (onBoundsChange != null) {
      let _timer = new Timer(new Duration({ milliseconds: 20 }), () => {
        if (_timer == null) {
          return;
        }
        const result = prepareBox(e);
        if (result == null) {
          return;
        }
        const { renderBox, parentWidth } = result;
        let size = renderBox.size;
        let rect = ui.Rect.fromLTWH(0, 0, size.width, size.height);
        if (!bounds?.isSame(rect)) {
          bounds = rect;
          onBoundsChange(rect, ui.Offset.zero, parentWidth);
        }
      });
    }
  };
}
