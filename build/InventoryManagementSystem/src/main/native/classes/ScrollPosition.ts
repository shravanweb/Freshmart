import React from "react";
import { ScrollPhysics } from "./ScrollPhysics";
import { ScrollActivity, ScrollContext } from "./ScrollActivity";
import { ListView, SingleChildScrollView } from "..";

export abstract class ScrollPosition {
  activity: ScrollActivity;
  allowImplicitScrolling: boolean = false;
  context: ScrollContext;
  debugLabel: string = " ";
  haveDimensions: boolean = false;
  isScrollingNotifier: boolean = false;
  keepScrollOffset: boolean = false;
  maxScrollExtent: boolean = false;
  minScrollExtent: number;
  physics: ScrollPhysics;
  pixels: number;
  oldPosition: ScrollPosition;
  viewportDimension: number;

  // constructor(params?: Partial<ScrollPosition>) {
  //   Object.assign(this, params);
  // }
  private element: HTMLElement;

  constructor(element: HTMLElement) {
    this.element = element;
  }

  getScrollableElement(): HTMLElement {
    return this.element;
  }
}
