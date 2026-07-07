package rest;

import d3e.core.CurrentUser;
import d3e.core.RestControllerBase;
import models.BaseUser;
import models.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CategoryImageController extends RestControllerBase {
  @Autowired private CategoryImageStore categoryImageStore;

  @PostMapping("/api/category-image")
  public void uploadCategoryImage(
      @RequestParam("code") String code, @RequestParam("file") MultipartFile file) {
    BaseUser currentUser = CurrentUser.get();
    if (!(currentUser instanceof User)) {
      markForbidden();
      return;
    }
    if (code == null || code.isBlank() || file == null || file.isEmpty()) {
      markBadRequest();
      return;
    }

    try {
      String ext = categoryImageStore.extensionFor(file.getOriginalFilename());
      categoryImageStore.clearOtherExtensions(code, ext);
      java.io.File target = categoryImageStore.targetFile(code, file.getOriginalFilename());
      file.transferTo(target);

      JSONObject response = new JSONObject();
      response.put(
          "imageUrl",
          "/images/categories/" + categoryImageStore.toSlug(code) + ext);
      writeJsonToResponse(response.toString());
    } catch (Exception exception) {
      exception.printStackTrace();
      markServerError();
    }
  }
}
