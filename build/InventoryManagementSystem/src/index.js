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

bootstrap(defaultSettings);

fetch("resource/settings.json")
  .then((r) => r.json())
  .then((settings) => {
    Env.get().load(settings);
  })
  .catch(() => {});
