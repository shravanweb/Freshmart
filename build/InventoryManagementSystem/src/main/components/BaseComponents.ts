import React, { Component, DOMAttributes, Key, ReactNode } from "react";
import DateTime from "../core/DateTime";
import * as ui from "../native/index";

export class Shader {}

async function showDatePicker(props: {
  context: any;
  initialDate: DateTime;
  firstDate: DateTime;
  lastDate: DateTime;
}): Promise<DateTime> {
  // TODO
  return null;
}

async function showTimePicker(props: {
  context: any;
  initialTime: ui.TimeOfDay;
}): Promise<ui.TimeOfDay> {
  // TODO
  return null;
}

export { showDatePicker, showTimePicker };
