package rest;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CategoryImageStore {
  private static final String[] EXTENSIONS = {".jpg", ".jpeg", ".png", ".webp"};

  @Value("${server.localstore:out}")
  private String localStore;

  public File getCategoriesDirectory() {
    File dir = Paths.get(localStore, "category-images").toAbsolutePath().normalize().toFile();
    if (!dir.exists()) {
      dir.mkdirs();
    }
    return dir;
  }

  public String toSlug(String code) {
    if (code == null) {
      return "";
    }
    String slug = code.trim().toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "-");
    while (slug.startsWith("-")) {
      slug = slug.substring(1);
    }
    while (slug.endsWith("-")) {
      slug = slug.substring(0, slug.length() - 1);
    }
    return slug;
  }

  public String extensionFor(String originalFilename) {
    if (originalFilename == null || !originalFilename.contains(".")) {
      return ".jpg";
    }
    String ext = originalFilename.substring(originalFilename.lastIndexOf('.')).toLowerCase(Locale.ROOT);
    if (".jpg".equals(ext) || ".jpeg".equals(ext) || ".png".equals(ext) || ".webp".equals(ext)) {
      return ext;
    }
    return ".jpg";
  }

  public String buildImageUrl(String code) {
    return "/api/category-image/file?code="
        + URLEncoder.encode(code.trim(), StandardCharsets.UTF_8);
  }

  public File resolveImageFile(String code) {
    String slug = toSlug(code);
    if (slug.isEmpty()) {
      return null;
    }
    File dir = getCategoriesDirectory();
    for (String ext : EXTENSIONS) {
      File target = new File(dir, slug + ext);
      if (target.exists() && target.isFile()) {
        return target;
      }
    }
    String legacy = legacyFileName(code);
    if (legacy != null) {
      File target = new File(dir, legacy);
      if (target.exists() && target.isFile()) {
        return target;
      }
    }
    return null;
  }

  public String resolveImageUrl(String code) {
    return resolveImageFile(code) != null ? buildImageUrl(code) : null;
  }

  public File targetFile(String code, String originalFilename) {
    String slug = toSlug(code);
    String ext = extensionFor(originalFilename);
    return new File(getCategoriesDirectory(), slug + ext);
  }

  public void clearOtherExtensions(String code, String keepExtension) {
    String slug = toSlug(code);
    if (slug.isEmpty()) {
      return;
    }
    File dir = getCategoriesDirectory();
    for (String ext : EXTENSIONS) {
      if (!ext.equalsIgnoreCase(keepExtension)) {
        File existing = new File(dir, slug + ext);
        if (existing.exists()) {
          existing.delete();
        }
      }
    }
  }

  private String legacyFileName(String code) {
    if (code == null) {
      return null;
    }
    switch (code.trim().toUpperCase(Locale.ROOT)) {
      case "DAIRY":
        return "dairy.jpg";
      case "DRY":
        return "dry-goods.jpg";
      case "FRZ":
        return "frozen.jpg";
      case "HOUSE":
        return "household.jpg";
      case "PERS":
        return "personal-care.jpg";
      case "PROD":
        return "produce.jpg";
      case "BEV":
        return "snacks-beverages.jpg";
      case "STAPLES":
        return "staples.jpg";
      default:
        return null;
    }
  }
}
