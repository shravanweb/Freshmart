import { Border } from "./Border";
import { Diagnosticable } from "./Diagnosticable";

export default class Decoration extends Diagnosticable {
  border?: Border;

  constructor(params?: { border?: Border }) {
    super();
    this.border = params?.border;
  }
}
