import { Runnable } from "../classes/core";
import * as ui from "../native";
import { OverlayController } from "../native/ui/Overlay";

export default class PopupTargetController {
  updates: Runnable[] = [];
  bounds: ui.Rect = ui.Rect.zero;
  globalPos: ui.Offset = ui.Offset.zero;
  scrollX: number = 0.0;
  scrollY: number = 0.0;
  overlay: OverlayController;
  ref: HTMLElement;

  handleRef = (e: HTMLElement) => {
    this.ref = e;
  };

  prepareBox(e: HTMLElement): ui.RenderBox {
    if (!e) {
      return null;
    }
    let ref = e;
    let rect = e.getBoundingClientRect();
    return new ui.RenderBox({
      size: new ui.Size({ width: rect.width, height: rect.height }),
      position: new ui.Offset({ dx: rect.left, dy: rect.top }),
    });
  }

  update = () => {
    let box: ui.RenderBox = this.prepareBox(this.ref);
    if (box == null) {
      return;
    }
    let pos = box.localToGlobal(ui.Offset.zero);
    let globalPos = box.localToGlobal(ui.Offset.zero);
    let size = box.size;
    let rect = ui.Rect.fromLTWH(pos.dx, pos.dy, size.width, size.height);
    if (!this.bounds?.isSame(rect)) {
      this.bounds = rect;
      this.globalPos = globalPos;
      let parent = undefined; //_ScrollWrapper.of(this.context);
      this.scrollX = parent?.dx ?? 0.0;
      this.scrollY = parent?.dy ?? 0.0;
    }
  };

  setContext(overlay: OverlayController) {
    this.overlay = overlay;
  }

  getTarget(context: any): ui.Rect {
    this.update();
    var target = this.bounds;
    return target;
  }
}
