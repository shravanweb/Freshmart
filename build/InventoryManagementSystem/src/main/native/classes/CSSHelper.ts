import { CrossAxisAlignment } from "./CrossAxisAlignment";
import { MainAxisAlignment } from "./MainAxisAlignment";
import { AlignmentDirectional } from "../classes/AlignmentDirectional";

export default class CSSHelper {
  static handleMainAxisAlignment(maa?: MainAxisAlignment): string[] {
    let classNames = [];
    if (maa) {
      switch (maa) {
        case MainAxisAlignment.start:
          classNames.push("ma-s");
          break;
        case MainAxisAlignment.end:
          classNames.push("ma-e");
          break;
        case MainAxisAlignment.center:
          classNames.push("ma-c");
          break;
        case MainAxisAlignment.spaceAround:
          classNames.push("ma-sa");
          break;
        case MainAxisAlignment.spaceBetween:
          classNames.push("ma-sb");
          break;
        case MainAxisAlignment.spaceEvenly:
          classNames.push("ma-se");
          break;
      }
    }
    return classNames;
  }

  static handleCrossAxisAlignment(caa?: CrossAxisAlignment): string[] {
    let classNames = [];
    if (caa) {
      switch (caa) {
        case CrossAxisAlignment.start:
          classNames.push("cr-s");
          break;
        case CrossAxisAlignment.end:
          classNames.push("cr-e");
          break;
        case CrossAxisAlignment.center:
          classNames.push("cr-c");
          break;
        case CrossAxisAlignment.stretch:
          classNames.push("cr-st");
          break;
        case CrossAxisAlignment.baseline:
          classNames.push("cr-b");
          break;
      }
    }
    return classNames;
  }

  static handleAlignmentDirectional(
    alignment?: AlignmentDirectional
  ): string[] {
    const classNames: string[] = [];

    switch (alignment) {
      case AlignmentDirectional.topStart:
        classNames.push("ad-top-start");
        break;

      case AlignmentDirectional.topCenter:
        classNames.push("ad-top-center");
        break;

      case AlignmentDirectional.topEnd:
        classNames.push("ad-top-end");
        break;

      case AlignmentDirectional.centerStart:
        classNames.push("ad-center-start");
        break;

      case AlignmentDirectional.center:
        classNames.push("ad-center");
        break;

      case AlignmentDirectional.centerEnd:
        classNames.push("ad-center-end");
        break;

      case AlignmentDirectional.bottomStart:
        classNames.push("ad-bottom-start");
        break;

      case AlignmentDirectional.bottomCenter:
        classNames.push("ad-bottom-center");
        break;

      case AlignmentDirectional.bottomEnd:
        classNames.push("ad-bottom-end");
        break;
    }

    return classNames;
  }
}
