import { FocusNode } from "./FocusNode";

export class FocusScopeNode extends FocusNode {
  focusedChild?: FocusNode;

  isFirstFocus?: Boolean;

  nearestScope?: FocusScopeNode;

  constructor(params: Partial<FocusScopeNode>) {
    super(params);
  }
}
