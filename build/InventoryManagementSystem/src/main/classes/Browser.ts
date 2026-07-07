import { ta } from "date-fns/locale";
import DFile from "./DFile";
import FileToUpload from "./FileToUpload";

export default class Browser {
  static async selectFile(
    extns: Array<string>,
    onCancel: () => void
  ): Promise<FileToUpload | null> {
    let contentType: string;
    if (extns != null && extns.length > 0) {
      extns = extns
        .map((e) => {
          if (!e.startsWith(".")) {
            e = "." + e;
          }
          return e;
        })
        .toList();
      contentType = extns.join(",");
    }
    let file = await Browser.loadFile(contentType, false);
    if (file == null) {
      onCancel();
      return null;
    } else {
      return new FileToUpload(file);
    }
  }
  static async loadFile(
    contentType: string,
    multiple: boolean
  ): Promise<File | null> {
    return new Promise<File | null>((resolve) => {
      let input = document.createElement("input");
      input.type = "file";
      input.multiple = multiple;
      if (contentType != null && contentType.length > 0) {
        input.accept = contentType;
      }
      input.onchange = (_) => {
        let files = Array.from(input.files);
        if (multiple)
          resolve(files as any); // casting for simplicity, adjust as needed
        else resolve(files[0] || null);
      };
      input.click();
    });
  }
  static download(_file: DFile) {
    let url: string = _file.downloadUrl + "?&originalName=" + _file.name;
    // eslint-disable-next-line no-restricted-globals
    open(url);
  }
  static sendMailTo(
    toEmail: string,
    emailDetails: { subject?: string; body?: string }
  ): void {
    let email = toEmail;
    let params: string[] = [];

    // Extract subject and body from the object
    const { subject, body } = emailDetails;

    if (body) {
      if (subject) {
        params.push("subject=" + encodeURIComponent(subject));
      }
      params.push("body=" + encodeURIComponent(body));
    }

    if (params.length > 0) {
      email += "?" + params.join("&");
    }

    window.location.href = "mailto:" + email;
  }
  static open(url: string, target: string = "_blank"): void {
    window.open(url, target);
  }
  static print(): void {
    window.print();
  }
}
