import Env from "../classes/Env";
export class DomUtils {
  static loadTheme(name: string) {
    let head = document.getElementsByTagName("head")[0];
    let divChildren = head.childNodes;
    let href = "static/styles/" + name + Env.get().buildVersion + ".css";
    for (var i = 0; i < divChildren.length; i++) {
      let title = divChildren[i]["title"];
      if (title && title == "theme") {
        let ref = divChildren[i]["href"];
        if (ref.endsWith(href)) {
          return;
        }
        divChildren[i].remove();
        break;
      }
    }
    let fileref = document.createElement("link");
    fileref.setAttribute("rel", "stylesheet");
    fileref.setAttribute("type", "text/css");
    fileref.setAttribute("href", href);
    fileref.setAttribute("title", "theme");
    document.getElementsByTagName("head")[0].appendChild(fileref);
  }
}
