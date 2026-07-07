package classes;

import d3e.core.DFile;
import d3e.core.D3ETempResourceHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Store;
import models.User;
import models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import repository.jpa.UserProfileRepository;
import store.Database;

@Service
public class StaffAccountService {
  @Autowired private UserProfileRepository userProfileRepository;
  @Autowired private D3ETempResourceHandler saveHandler;

  public Map<String, Object> getProfile(User user) {
    UserProfile profile = resolveProfile(user);
    if (profile == null) {
      return null;
    }
    return toProfileMap(user, profile);
  }

  public Map<String, Object> updateProfile(User user, String displayName, String phone) {
    UserProfile profile = resolveProfile(user);
    if (profile == null) {
      return null;
    }
    if (displayName != null && !displayName.trim().isEmpty()) {
      profile.setDisplayName(displayName.trim());
    }
    if (phone != null) {
      profile.setPhone(phone.trim());
    }
    Database.get().save(profile);
    return toProfileMap(user, profile);
  }

  public Map<String, Object> uploadAvatar(User user, MultipartFile file) throws Exception {
    UserProfile profile = resolveProfile(user);
    if (profile == null || file == null || file.isEmpty()) {
      return null;
    }
    String fileName = file.getOriginalFilename();
    if (fileName == null || fileName.isBlank()) {
      fileName = "avatar.jpg";
    }
    DFile avatar = saveHandler.save(fileName, file.getInputStream());
    profile.setAvatar(avatar);
    Database.get().save(profile);
    return toProfileMap(user, profile);
  }

  public Map<String, Object> removeAvatar(User user) {
    UserProfile profile = resolveProfile(user);
    if (profile == null) {
      return null;
    }
    profile.setAvatar(null);
    Database.get().save(profile);
    return toProfileMap(user, profile);
  }

  private UserProfile resolveProfile(User user) {
    if (user == null) {
      return null;
    }
    return userProfileRepository.getByUser(user);
  }

  private Map<String, Object> toProfileMap(User user, UserProfile profile) {
    Map<String, Object> result = new HashMap<>();
    result.put("id", profile.getId());
    result.put("displayName", profile.getDisplayName() != null ? profile.getDisplayName() : "");
    result.put("phone", profile.getPhone() != null ? profile.getPhone() : "");
    result.put(
        "email",
        user != null && user.getEmail() != null ? user.getEmail().trim().toLowerCase() : "");
    result.put(
        "appRole",
        profile.getAppRole() != null ? profile.getAppRole().name() : "");
    result.put("avatarUrl", resolveAvatarUrl(profile.getAvatar()));

    List<Map<String, Object>> assignedStores = new ArrayList<>();
    if (profile.getAssignedStores() != null) {
      for (Store store : profile.getAssignedStores()) {
        if (store == null) {
          continue;
        }
        Map<String, Object> storeMap = new HashMap<>();
        storeMap.put("id", store.getId());
        storeMap.put("name", store.getName() != null ? store.getName() : "");
        storeMap.put("code", store.getCode() != null ? store.getCode() : "");
        assignedStores.add(storeMap);
      }
    }
    result.put("assignedStores", assignedStores);
    return result;
  }

  private String resolveAvatarUrl(DFile avatar) {
    if (avatar == null || avatar.getId() == null || avatar.getId().isBlank()) {
      return "";
    }
    return "/api/download/" + avatar.getId() + "?inline=true";
  }
}
