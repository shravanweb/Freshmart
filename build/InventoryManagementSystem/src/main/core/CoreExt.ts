import ArrayExt from "./Array";
import BigIntExt from "./BigInt";
import BooleanExt from "./Boolean";
import FutureExt from "./Future";
import IterableExt from "./Iterable";
import IteratorExt from "./Iterator";
import CoreMapExt from "./Map";
import NumberExt from "./Number";
import ObjectExt from "./Object";
import QueueExt from "./Queue";
import RegExt from "./RegExp";
import SetExt from "./Set";
import StringExt from "./String";

export default class CoreExt {
  constructor() {
    new ArrayExt();
    new BigIntExt();
    new BooleanExt();
    new FutureExt();
    new IterableExt();
    new IteratorExt();
    new CoreMapExt();
    new NumberExt();
    new ObjectExt();
    new QueueExt();
    new RegExt();
    new SetExt();
    new StringExt();
  }
}
