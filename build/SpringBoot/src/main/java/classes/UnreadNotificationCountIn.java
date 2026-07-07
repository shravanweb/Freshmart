package classes;

public class UnreadNotificationCountIn {
  public long organization;
  public long user;

  public UnreadNotificationCountIn() {}

  public UnreadNotificationCountIn(long organization, long user) {
    this.organization = organization;
    this.user = user;
  }
}
