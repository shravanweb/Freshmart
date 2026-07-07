export class IconData {
  fontFamily?: string;
  fontPackage?: string;
  matchTextDirection?: boolean;

  public constructor(
    public codePoint: number,
    params?: Partial<{
      fontFamily: string;
      fontPackage: string;
      matchTextDirection: boolean;
    }>
  ) {
    this.fontFamily = params?.fontFamily;
    this.fontPackage = params?.fontPackage;
    this.matchTextDirection = params?.matchTextDirection;
  }
}
