import DFile from "./DFile";
import Env from "./Env";
import FileToUpload from "./FileToUpload";
import FileUploadResult from "./FileUploadResult";
import LocalDataStore from "../utils/LocalDataStore";

export default class PlatformClient {
  static async upload(fileToUpload: FileToUpload): Promise<FileUploadResult> {
    return PlatformClient._doUpload(fileToUpload);
  }
  static async _doUpload(
    fileToUpload: FileToUpload
  ): Promise<FileUploadResult> {
    const formData = new FormData();
    formData.append("file", fileToUpload.file);
    const baseUrl = Env.get().resolvedHttpUrl;
    const token = await LocalDataStore.get().getToken();
    const headers: Record<string, string> = {};
    if (token) {
      headers.Authorization = "Bearer " + token;
    }

    try {
      const response = await fetch(baseUrl + "/api/upload", {
        method: "POST",
        headers,
        body: formData,
      });
      const bodyText = await response.text();
      if (response.status === 200) {
        const obj = JSON.parse(bodyText);
        const map = new Map<string, any>();
        map.set("name", obj.name);
        map.set("id", obj.id);
        map.set("size", obj.size);
        map.set("mimeType", obj.mimeType);
        return new FileUploadResult(DFile.fromJson(map), true, "");
      }

      let errorMessage = response.statusText || "Upload failed.";
      try {
        const parsed = JSON.parse(bodyText);
        if (parsed?.message) {
          errorMessage = String(parsed.message);
        } else if (Array.isArray(parsed?.errors) && parsed.errors.length > 0) {
          errorMessage = String(parsed.errors[0]?.message ?? parsed.errors[0]);
        }
      } catch (_ignored) {
        if (bodyText.trim()) {
          errorMessage = bodyText.trim();
        }
      }
      return new FileUploadResult(null, false, errorMessage);
    } catch (error: unknown) {
      const message = error instanceof Error ? error.message : String(error);
      return new FileUploadResult(null, false, message);
    }
  }
}
