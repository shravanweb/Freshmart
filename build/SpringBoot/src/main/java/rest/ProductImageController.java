package rest;

import d3e.core.CurrentUser;
import d3e.core.RestControllerBase;
import java.util.List;
import models.BaseUser;
import models.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ProductImageController extends RestControllerBase {
  @Autowired private ProductImageStore productImageStore;

  private boolean isAuthorized() {
    BaseUser currentUser = CurrentUser.get();
    return currentUser instanceof User;
  }

  @GetMapping("/api/product-image")
  public void listProductImages(@RequestParam("productId") Long productId) {
    if (!isAuthorized()) {
      markForbidden();
      return;
    }
    if (productId == null || productId <= 0) {
      markBadRequest();
      return;
    }

    List<String> imageUrls = productImageStore.listImageUrls(productId);
    JSONArray items = new JSONArray();
    for (String imageUrl : imageUrls) {
      items.put(imageUrl);
    }

    JSONObject response = new JSONObject();
    response.put("items", items);
    writeJsonToResponse(response.toString());
  }

  @PostMapping("/api/product-image")
  public void uploadProductImages(
      @RequestParam("productId") Long productId, @RequestParam("file") MultipartFile[] files) {
    if (!isAuthorized()) {
      markForbidden();
      return;
    }
    if (productId == null || productId <= 0 || files == null || files.length == 0) {
      markBadRequest();
      return;
    }

    try {
      JSONArray uploaded = new JSONArray();
      for (MultipartFile file : files) {
        if (file == null || file.isEmpty()) {
          continue;
        }
        String imageUrl =
            productImageStore.saveImage(
                productId, file.getOriginalFilename(), file.getBytes());
        uploaded.put(imageUrl);
      }

      if (uploaded.length() == 0) {
        markBadRequest();
        return;
      }

      JSONObject response = new JSONObject();
      response.put("items", uploaded);
      writeJsonToResponse(response.toString());
    } catch (Exception exception) {
      exception.printStackTrace();
      markServerError();
    }
  }

  @DeleteMapping("/api/product-image")
  public void deleteProductImage(
      @RequestParam("productId") Long productId, @RequestParam("filename") String filename) {
    if (!isAuthorized()) {
      markForbidden();
      return;
    }
    if (productId == null || productId <= 0 || filename == null || filename.isBlank()) {
      markBadRequest();
      return;
    }

    if (!productImageStore.deleteImage(productId, filename)) {
      markNotFound();
      return;
    }

    JSONObject response = new JSONObject();
    response.put("success", true);
    writeJsonToResponse(response.toString());
  }
}
