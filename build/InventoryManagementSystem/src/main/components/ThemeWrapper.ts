import * as ui from "../native";
import React, { ReactNode } from "react";
import { DomUtils } from "../utils/DomUtils";
import { BuildContext, ContextData } from "../classes/BuildContext";

export class StyleThemeData {
  public readonly themeName: string;
  public readonly color: ui.Color;
  public readonly textStyle: ui.TextStyle;
  public readonly tooltipBackgroundColor: ui.Color;
  public readonly tooltipTextColor: ui.Color;
  public static IMSTheme: StyleThemeData;
  public readonly c1: ui.Color;
  public readonly c2: ui.Color;
  public readonly c3: ui.Color;
  public readonly c4: ui.Color;
  public readonly c5: ui.Color;
  public readonly c6: ui.Color;
  public readonly c7: ui.Color;
  public readonly c8: ui.Color;
  public readonly c9: ui.Color;
  public readonly c10: ui.Color;
  public readonly c11: ui.Color;
  public readonly c12: ui.Color;
  public readonly c13: ui.Color;
  public readonly c14: ui.Color;
  public readonly c15: ui.Color;
  public readonly c16: ui.Color;
  public readonly c25: ui.Color;
  public static current: StyleThemeData;
  public constructor(
    d3eParams?: Partial<{
      themeName: string;
      color: ui.Color;
      textStyle: ui.TextStyle;
      tooltipBackgroundColor: ui.Color;
      tooltipTextColor: ui.Color;
      c1: ui.Color;
      c2: ui.Color;
      c3: ui.Color;
      c4: ui.Color;
      c5: ui.Color;
      c6: ui.Color;
      c7: ui.Color;
      c8: ui.Color;
      c9: ui.Color;
      c10: ui.Color;
      c11: ui.Color;
      c12: ui.Color;
      c13: ui.Color;
      c14: ui.Color;
      c15: ui.Color;
      c16: ui.Color;
      c25: ui.Color;
    }>
  ) {
    this.themeName = d3eParams?.themeName;

    this.color = d3eParams?.color;

    this.textStyle = d3eParams?.textStyle;

    this.tooltipBackgroundColor = d3eParams?.tooltipBackgroundColor;

    this.tooltipTextColor = d3eParams?.tooltipTextColor;

    this.c1 = d3eParams?.c1;

    this.c2 = d3eParams?.c2;

    this.c3 = d3eParams?.c3;

    this.c4 = d3eParams?.c4;

    this.c5 = d3eParams?.c5;

    this.c6 = d3eParams?.c6;

    this.c7 = d3eParams?.c7;

    this.c8 = d3eParams?.c8;

    this.c9 = d3eParams?.c9;

    this.c10 = d3eParams?.c10;

    this.c11 = d3eParams?.c11;

    this.c12 = d3eParams?.c12;

    this.c13 = d3eParams?.c13;

    this.c14 = d3eParams?.c14;

    this.c15 = d3eParams?.c15;

    this.c16 = d3eParams?.c16;

    this.c25 = d3eParams?.c25;
  }
  public static createIMSTheme(): StyleThemeData {
    StyleThemeData.IMSTheme = new StyleThemeData({
      themeName: "IMSTheme",
      color: new ui.Color(0xfffafaf9),
      textStyle: new ui.TextStyle({
        color: new ui.Color(0xff000000),
        fontFamily: "Inter",
        fontSize: 15.0,
      }),
      tooltipBackgroundColor: new ui.Color(0xffffffff),
      tooltipTextColor: new ui.Color(0xff000000),
      c1: new ui.Color(0xfff59e0b),
      c2: new ui.Color(0xfff97316),
      c3: new ui.Color(0xfffbbf24),
      c4: new ui.Color(0xff0f172a),
      c5: new ui.Color(0xff64748b),
      c6: new ui.Color(0xffe7e5e4),
      c7: new ui.Color(0xffc62828),
      c8: new ui.Color(0xff22c55e),
      c9: new ui.Color(0xfff5f5f4),
      c10: new ui.Color(0xd9ffffff),
      c11: new ui.Color(0xffffedd5),
      c12: new ui.Color(0xffd1fae5),
      c13: new ui.Color(0xfffef3c7),
      c14: new ui.Color(0xd9ffffff),
      c15: new ui.Color(0xfffffbeb),
      c16: new ui.Color(0xfffff1f2),
      c25: new ui.Color(0xffffffff),
    });

    return StyleThemeData.IMSTheme;
  }
}

interface ThemeWrapperProps {
  child: ReactNode;
}

class _ThemeWrapper extends React.Component<ThemeWrapperProps> {
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  private static _themeLoaded = false;
  private _providerValue: ContextData | null = null;

  private getProviderValue(): ContextData {
    const theme = StyleThemeData.current;

    if (this._providerValue == null || this._providerValue.theme !== theme) {
      this._providerValue = { theme };
    }

    return this._providerValue;
  }

  public render(): React.ReactNode {
    if (!_ThemeWrapper._themeLoaded) {
      DomUtils.loadTheme(StyleThemeData.current.themeName);

      _ThemeWrapper._themeLoaded = true;
    }

    return React.createElement(
      BuildContext.Provider,
      { value: this.getProviderValue() },
      this.props.child
    );
  }
}
export default function ThemeWrapper(props: ThemeWrapperProps) {
  return React.createElement(_ThemeWrapper, props);
}

export const ThemeContext = React.createContext<StyleThemeData>(null);
