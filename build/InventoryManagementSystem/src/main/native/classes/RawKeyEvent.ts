import { LogicalKeyboardKey } from "./LogicalKeyboardKey";

export class RawKeyEvent {
  character: string;
  isAltPressed: boolean;
  isControlPressed: boolean;
  isMetaPressed: boolean;
  isShiftPressed: boolean;
  logicalKey: LogicalKeyboardKey;
  down: boolean;

  constructor(params?: Partial<RawKeyEvent>) {
    Object.assign(this, params);
  }
}
