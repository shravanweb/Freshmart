package rest;

import d3e.core.CurrentUser;
import d3e.core.RestControllerBase;
import java.io.File;
import models.BaseUser;
import models.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CategoryImageController extends RestControllerBase {
  @Autowired private CategoryImageStore categoryImageStore;

  @GetMapping("/api/category-image/file")
  public ResponseEntity<Resource> getCategoryImageFile(@RequestParam("code") String code) {
    if (code == null || code.isBlank()) {
      return ResponseEntity.badRequest().build();
    }

    File imageFile = categoryImageStore.resolveImageFile(code);
    if (imageFile == null) {
      return ResponseEntity.notFound().build();
    }

    Resource resource = new FileSystemResource(imageFile);
    String filename = imageFile.getName().toLowerCase();
    String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
    if (filename.endsWith(".png")) {
      contentType = MediaType.IMAGE_PNG_VALUE;
    } else if (filename.endsWith(".webp")) {
      contentType = "image/webp";
    } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
      contentType = MediaType.IMAGE_JPEG_VALUE;
    }

    return ResponseEntity.ok()
        .header(HttpHeaders.CACHE_CONTROL, "public, max-age=86400")
        .contentType(MediaType.parseMediaType(contentType))
        .body(resource);
  }

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
      response.put("imageUrl", categoryImageStore.buildImageUrl(code));
      writeJsonToResponse(response.toString());
    } catch (Exception exception) {
      exception.printStackTrace();
      markServerError();
    }
  }
}
