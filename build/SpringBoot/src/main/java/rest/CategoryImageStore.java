package rest;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import org.springframework.stereotype.Service;

@Service
public class CategoryImageStore {
  private static final String[] EXTENSIONS = {".jpg", ".jpeg", ".png", ".webp"};

  public File getCategoriesDirectory() {
    Path fromCwd =
        Paths.get(System.getProperty("user.dir"))
            .resolve("../InventoryManagementSystem/public/images/categories")
            .normalize();
    File dir = fromCwd.toFile();
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

  public String resolveImageUrl(String code) {
    String slug = toSlug(code);
    if (slug.isEmpty()) {
      return null;
    }
    File dir = getCategoriesDirectory();
    for (String ext : EXTENSIONS) {
      if (new File(dir, slug + ext).exists()) {
        return "/images/categories/" + slug + ext;
      }
    }
    String legacy = legacyFileName(code);
    if (legacy != null && new File(dir, legacy).exists()) {
      return "/images/categories/" + legacy;
    }
    return null;
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
