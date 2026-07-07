class ElementUtils {
  static removeInitialLoader(): void {
    this.removeElementWithId("wrapper");
  }

  static removeElementWithId(id: string): void {
    const divElement = document.getElementsByClassName(id);
    if (divElement.length > 0) {
      divElement[0].remove();
    }
  }
}

export default ElementUtils;
