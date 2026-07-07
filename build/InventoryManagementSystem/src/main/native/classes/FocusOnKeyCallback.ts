import { FocusNode } from "./FocusNode";
import { RawKeyEvent } from "./RawKeyEvent";

export type FocusOnKeyCallback = (
  node: FocusNode,
  event: RawKeyEvent
) => boolean;
