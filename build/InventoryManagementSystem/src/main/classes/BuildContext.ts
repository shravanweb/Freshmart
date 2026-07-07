import React from "react";
import { TextStyle } from "../native";
import { StyleThemeData } from "../components/ThemeWrapper";
import Popup from "../components/Popup";

type LayoutBoundsProvider = () => HTMLInputElement;

export interface ContextData {
  layoutBoundsProvider?: LayoutBoundsProvider;
  theme?: StyleThemeData;
  textStyle?: TextStyle;
  popup?: Popup;
}
export const BuildContext = React.createContext<ContextData>({});
