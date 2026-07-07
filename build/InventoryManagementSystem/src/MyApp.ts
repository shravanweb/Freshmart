import { Component, ReactNode } from "react";
import PageNavigator from "./main/classes/PageNavigator";
import AppBootstrap from "./AppBootstrap";
import ThemeWrapper from "./main/components/ThemeWrapper";
import { StyleThemeData } from "./main/components/ThemeWrapper";
import { DefaultPopupOverlay } from "./main/native";

export default class MyApp extends Component<{}, { theme: StyleThemeData }> {
  public constructor(props: any) {
    super(props);

    StyleThemeData.current = StyleThemeData.createIMSTheme();

    this.state = { theme: StyleThemeData.current };

    PageNavigator.themeUpdateListener = () => {
      if (this.state.theme !== StyleThemeData.current) {
        this.setState({ theme: StyleThemeData.current });
      }
    };
  }
  public render() {
    return ThemeWrapper({
      child: DefaultPopupOverlay({
        child: AppBootstrap({}),
      }),
    });
  }
}
