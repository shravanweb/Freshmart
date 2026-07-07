import * as ui from "../native";
import MaterialIcons from "../icons/MaterialIcons";
import MaterialIconsOutlined from "../icons/MaterialIconsOutlined";
import MaterialIconsRound from "../icons/MaterialIconsRound";
import MaterialIconsSharp from "../icons/MaterialIconsSharp";
import MaterialIconsTwoTone from "../icons/MaterialIconsTwoTone";
import MaterialSymbolsOutlined from "../icons/MaterialSymbolsOutlined";
import MaterialSymbolsRound from "../icons/MaterialSymbolsRound";
import MaterialSymbolsSharp from "../icons/MaterialSymbolsSharp";

export default class IconDataUtils {
  public static getIconData(iconName: string): ui.IconData {
    // null check
    if (!iconName) {
      return new ui.IconData(59531, { fontFamily: "MaterialIcons" });
    }

    const parts = iconName.split(".");

    // malformed string check
    if (parts.length !== 2) {
      return new ui.IconData(59531, { fontFamily: "MaterialIcons" });
    }

    const fontFamily = parts[0];
    const iconKey = parts[1];

    // empty parts check
    if (!fontFamily || !iconKey) {
      return new ui.IconData(59531, { fontFamily: "MaterialIcons" });
    }

    if (fontFamily === "MaterialIcons") {
      const iconData = MaterialIcons.getIconDataByName(iconKey);
      if (iconData instanceof ui.IconData) {
        return iconData;
      } else {
        return new ui.IconData(59531, { fontFamily: "MaterialIcons" });
      }
    }

    if (fontFamily === "MaterialIconsOutlined") {
      const iconData = MaterialIconsOutlined.getIconDataByName(iconKey);
      if (iconData instanceof ui.IconData) {
        return iconData;
      }
      return new ui.IconData(59531, { fontFamily: "MaterialIconsOutlined" });
    }

    if (fontFamily === "MaterialIconsRound") {
      const iconData = MaterialIconsRound.getIconDataByName(iconKey);
      if (iconData instanceof ui.IconData) {
        return iconData;
      }
      return new ui.IconData(59531, { fontFamily: "MaterialIconsRound" });
    }

    if (fontFamily === "MaterialIconsSharp") {
      const iconData = MaterialIconsSharp.getIconDataByName(iconKey);
      if (iconData instanceof ui.IconData) {
        return iconData;
      }
      return new ui.IconData(59531, { fontFamily: "MaterialIconsSharp" });
    }

    if (fontFamily === "MaterialIconsTwoTone") {
      const iconData = MaterialIconsTwoTone.getIconDataByName(iconKey);
      if (iconData instanceof ui.IconData) {
        return iconData;
      }
      return new ui.IconData(59531, { fontFamily: "MaterialIconsTwoTone" });
    }

    if (fontFamily === "MaterialSymbolsOutlined") {
      const iconData = MaterialSymbolsOutlined.getIconDataByName(iconKey);
      if (iconData instanceof ui.IconData) {
        return iconData;
      }
      return new ui.IconData(59531, { fontFamily: "MaterialSymbolsOutlined" });
    }

    if (fontFamily === "MaterialSymbolsRound") {
      const iconData = MaterialSymbolsRound.getIconDataByName(iconKey);
      if (iconData instanceof ui.IconData) {
        return iconData;
      }
      return new ui.IconData(59531, { fontFamily: "MaterialSymbolsRound" });
    }

    if (fontFamily === "MaterialSymbolsSharp") {
      const iconData = MaterialSymbolsSharp.getIconDataByName(iconKey);
      if (iconData instanceof ui.IconData) {
        return iconData;
      }
      return new ui.IconData(59531, { fontFamily: "MaterialSymbolsSharp" });
    }

    // unknown fontFamily fallback
    return new ui.IconData(59531, { fontFamily: "MaterialIcons" });
  }
}
