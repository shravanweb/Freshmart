import { ReactNode } from "react";
import ChangeNotifier from "./ChangeNotifier";

export class OverlayEntry extends ChangeNotifier {
  key?: string;
  builder: (ctx: any) => ReactNode;
  opaque?: boolean;
  maintainState?: boolean;
  index: number;

  constructor({
    key,
    builder,
    opaque = false,
    maintainState = false,
  }: {
    key?: string;
    builder: (ctx: any) => ReactNode;
    opaque?: boolean;
    maintainState?: boolean;
  }) {
    super();
    this.key = key;
    this.builder = builder;
    this.opaque = opaque;
    this.maintainState = maintainState;
  }
}
