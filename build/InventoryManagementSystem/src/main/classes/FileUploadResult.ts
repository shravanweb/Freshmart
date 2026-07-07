import DFile from "./DFile";

export default class FileUploadResult {
  file: DFile;
  success: boolean;
  errorMessage: string;

  constructor(file: DFile, success: boolean, errorMessage: string) {
    this.file = file;
    this.success = success;
    this.errorMessage = errorMessage;
  }

  static failed(failedtoupload: string): FileUploadResult {
    return new FileUploadResult(null, false, failedtoupload);
  }
}
