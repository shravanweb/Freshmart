import React, { Component } from "react";
import ObservableComponent from "./ObservableComponent";

interface LoaderProps {}

class _Loader extends ObservableComponent<LoaderProps> {
  render() {
    return React.createElement("ui-loader", {});
  }
}

export default function Loader(props: LoaderProps) {
  return React.createElement(_Loader, props);
}
