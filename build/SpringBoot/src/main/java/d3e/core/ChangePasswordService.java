package d3e.core;

import classes.MutateResultStatus;
import models.AnonymousUser;
import models.BaseUser;
import models.ChangePasswordRequest;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.EntityMutator;
import store.ValidationFailedException;

@Service
public class ChangePasswordService {
  @Autowired private EntityMutator mutator;

  public void change(ChangePasswordRequest request) {
    if (request == null) {
      return;
    }
    BaseUser currentUser = CurrentUser.get();
    if (currentUser == null
        || !(currentUser instanceof AnonymousUser || currentUser instanceof User)) {
      throw new ValidationFailedException(
          MutateResultStatus.AuthFail,
          ListExt.asList("Invalid change password request for current user."));
    }
    String password = request.getNewPassword();
    if (currentUser instanceof User) {
      User user = ((User) currentUser);
      user.setPassword(password);
      this.mutator.update(user, true);
    }
  }
}
