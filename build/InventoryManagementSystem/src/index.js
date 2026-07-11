import React from "react";
import ReactDOM from "react-dom";
import Env from "./main/classes/Env";
import CoreExt from "./main/core/CoreExt";
import AllPagesUtil from "./main/utils/AllPagesUtil";
import MyApp from "./MyApp";

const defaultSettings = {
  baseHttpUrl: "",
  baseWSurl: "",
  buildNumber: "1",
  buildVersion: "",
};

function bootstrap(settings) {
  Env.get().load(settings);
  new CoreExt();
  AllPagesUtil.load();
  ReactDOM.render(<MyApp />, document.getElementById("root"));
}

async function main() {
  let settings = defaultSettings;
  try {
    const response = await fetch("resource/settings.json");
    if (response.ok) {
      settings = await response.json();
    }
  } catch {
    // Fall back to defaults for local dev without settings.json.
  }
  bootstrap(settings);
}

main();
