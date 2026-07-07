import { RawKeyEvent } from "./RawKeyEvent";

export default class RawKeyboard {
  static instance: RawKeyboard;

  addListener(listener: (event: RawKeyEvent) => void) {}

  removeListener(listener: (event: RawKeyEvent) => void) {}
}
