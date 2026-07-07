import { Offset } from "./Offset";
import { PointerSignalEvent } from "./PointerSignalEvent";

export class PointerScrollEvent extends PointerSignalEvent {
  scrollDelta: Offset;

  constructor(params?: Partial<PointerScrollEvent>) {
    super();
    Object.assign(this, params);
  }
}
