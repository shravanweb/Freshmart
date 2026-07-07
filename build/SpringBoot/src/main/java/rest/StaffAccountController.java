package rest;

import classes.StaffAccountService;
import d3e.core.CurrentUser;
import d3e.core.RestControllerBase;
import java.util.Map;
import models.BaseUser;
import models.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class StaffAccountController extends RestControllerBase {
  @Autowired private StaffAccountService staffAccountService;

  private User requireUser() {
    BaseUser currentUser = CurrentUser.get();
    if (currentUser instanceof User user) {
      return user;
    }
    return null;
  }

  @GetMapping("/api/staff/profile")
  public void getProfile() {
    User user = requireUser();
    if (user == null) {
      markForbidden();
      return;
    }

    Map<String, Object> profile = staffAccountService.getProfile(user);
    if (profile == null) {
      markNotFound();
      return;
    }

    writeJsonToResponse(new JSONObject(profile).toString());
  }

  @PostMapping("/api/staff/profile")
  public void updateProfile(@RequestBody String body) {
    User user = requireUser();
    if (user == null) {
      markForbidden();
      return;
    }

    JSONObject input = new JSONObject(body);
    String displayName = input.optString("displayName", "");
    String phone = input.optString("phone", "");

    Map<String, Object> profile = staffAccountService.updateProfile(user, displayName, phone);
    if (profile == null) {
      markNotFound();
      return;
    }

    writeJsonToResponse(new JSONObject(profile).toString());
  }

  @PostMapping("/api/staff/profile/avatar")
  public void uploadAvatar(@RequestParam("file") MultipartFile file) {
    User user = requireUser();
    if (user == null) {
      markForbidden();
      return;
    }
    if (file == null || file.isEmpty()) {
      markBadRequest();
      return;
    }

    try {
      Map<String, Object> profile = staffAccountService.uploadAvatar(user, file);
      if (profile == null) {
        markNotFound();
        return;
      }
      writeJsonToResponse(new JSONObject(profile).toString());
    } catch (Exception exception) {
      exception.printStackTrace();
      markServerError();
    }
  }

  @DeleteMapping("/api/staff/profile/avatar")
  public void removeAvatar() {
    User user = requireUser();
    if (user == null) {
      markForbidden();
      return;
    }

    Map<String, Object> profile = staffAccountService.removeAvatar(user);
    if (profile == null) {
      markNotFound();
      return;
    }

    writeJsonToResponse(new JSONObject(profile).toString());
  }
}
