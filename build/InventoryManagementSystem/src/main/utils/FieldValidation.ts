export default class FieldValidation {
  public static isEmail(email: string): boolean {
    const emailRegex = RegExp(
      "^[a-zA-Z0-9.!#$%&*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:.[a-zA-Z0-9-]+)*$"
    );
    return emailRegex.test(email);
  }
}
