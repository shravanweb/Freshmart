package classes;

public class UserDevicesIn {
  public long user;
  public String token;

  public UserDevicesIn() {}

  public UserDevicesIn(String token, long user) {
    this.user = user;
    this.token = token;
  }
}
