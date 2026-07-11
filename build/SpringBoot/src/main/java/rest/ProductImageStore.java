package rest;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProductImageStore {
  private static final String[] EXTENSIONS = {".jpg", ".jpeg", ".png", ".webp"};

  @Value("${server.localstore:out}")
  private String localStore;

  public File getProductsRootDirectory() {
    File dir = Paths.get(localStore, "product-images").toAbsolutePath().normalize().toFile();
    if (!dir.exists()) {
      dir.mkdirs();
    }
    return dir;
  }

  public File getProductDirectory(long productId) {
    File dir = new File(getProductsRootDirectory(), String.valueOf(productId));
    if (!dir.exists()) {
      dir.mkdirs();
    }
    return dir;
  }

  public String extensionFor(String originalFilename) {
    if (originalFilename == null || !originalFilename.contains(".")) {
      return ".jpg";
    }
    String ext =
        originalFilename.substring(originalFilename.lastIndexOf('.')).toLowerCase(Locale.ROOT);
    if (".jpg".equals(ext) || ".jpeg".equals(ext) || ".png".equals(ext) || ".webp".equals(ext)) {
      return ext;
    }
    return ".jpg";
  }

  public boolean isImageFile(String filename) {
    if (filename == null || filename.isBlank()) {
      return false;
    }
    String lower = filename.toLowerCase(Locale.ROOT);
    for (String ext : EXTENSIONS) {
      if (lower.endsWith(ext)) {
        return true;
      }
    }
    return false;
  }

  public List<String> listImageUrls(long productId) {
    File dir = getProductDirectory(productId);
    File[] files = dir.listFiles();
    if (files == null || files.length == 0) {
      return new ArrayList<>();
    }

    List<File> imageFiles = new ArrayList<>();
    for (File file : files) {
      if (file.isFile() && isImageFile(file.getName())) {
        imageFiles.add(file);
      }
    }

    imageFiles.sort(Comparator.comparing(File::getName));
    List<String> urls = new ArrayList<>();
    for (File file : imageFiles) {
      urls.add(buildImageUrl(productId, file.getName()));
    }
    return urls;
  }

  public String resolvePrimaryImageUrl(long productId) {
    List<String> urls = listImageUrls(productId);
    return urls.isEmpty() ? null : urls.get(0);
  }

  public File resolveImageFile(long productId, String filename) {
    if (!isImageFile(filename) || filename.contains("/") || filename.contains("\\")) {
      return null;
    }
    File target = new File(getProductDirectory(productId), filename);
    return target.exists() && target.isFile() ? target : null;
  }

  public String buildImageUrl(long productId, String filename) {
    return "/api/product-image/file?productId="
        + productId
        + "&filename="
        + URLEncoder.encode(filename, StandardCharsets.UTF_8);
  }

  public String saveImage(long productId, String originalFilename, byte[] bytes) throws Exception {
    File dir = getProductDirectory(productId);
    String ext = extensionFor(originalFilename);
    String filename = nextFilename(dir, ext);
    File target = new File(dir, filename);
    java.nio.file.Files.write(target.toPath(), bytes);
    return buildImageUrl(productId, filename);
  }

  public boolean deleteImage(long productId, String filename) {
    if (!isImageFile(filename) || filename.contains("/") || filename.contains("\\")) {
      return false;
    }
    File target = new File(getProductDirectory(productId), filename);
    return target.exists() && target.delete();
  }

  public void deleteAllImages(long productId) {
    File dir = getProductDirectory(productId);
    File[] files = dir.listFiles();
    if (files == null) {
      return;
    }
    for (File file : files) {
      if (file.isFile()) {
        file.delete();
      }
    }
  }

  private String nextFilename(File dir, String ext) {
    File[] existing = dir.listFiles();
    int max = 0;
    if (existing != null) {
      for (File file : existing) {
        if (!file.isFile()) {
          continue;
        }
        String name = file.getName();
        if (!name.startsWith("img-")) {
          continue;
        }
        int dot = name.lastIndexOf('.');
        String numberPart = dot > 4 ? name.substring(4, dot) : name.substring(4);
        try {
          max = Math.max(max, Integer.parseInt(numberPart));
        } catch (NumberFormatException ignored) {
          // ignore malformed names
        }
      }
    }
    return "img-" + (max + 1) + ext;
  }
}
