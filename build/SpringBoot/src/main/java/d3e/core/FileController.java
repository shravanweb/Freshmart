package d3e.core;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Objects;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class FileController {
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	private static final int TIMEOUT = 365 * 24 * 60 * 60;

	@Autowired
	private Map<String, D3EResourceHandler> handlers;

	@Autowired
	private D3ETempResourceHandler saveHandler;
	
	@Autowired
    protected ImageResizeService resizer;

	private static FileController _INIT;

	@PostConstruct
	public void init() {
		_INIT = this;
	}

	public static Resource load(DFile file) {
		D3EResourceHandler loadHandler = _INIT.handlers.getOrDefault(_INIT.getPrefix(file.getId()), null);
		if (loadHandler == null) {
			throw new RuntimeException("Resource not found.");
		}
		Resource resource = _INIT.loadFileAsResource(loadHandler, file.getId(), 0, 0);
		return resource;
	}

	@PostMapping("/api/upload")
	public DFile uploadFile(@RequestParam("file") MultipartFile multiFile) {
		String fileName = multiFile.getOriginalFilename();
		try {
			DFile file = saveHandler.save(fileName, multiFile.getInputStream());
			return file;
		} catch (Exception e) {
			Log.printStackTrace(e);
			throw new RuntimeException("Could not store file " + fileName + ". Please try again!", e);
		}
	}

	@PostMapping("/api/uploads")
	public List<DFile> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/api/download/{fileName}")
	public ResponseEntity<Resource> download(@PathVariable("fileName") String fileName,
			@RequestParam(required = false, name = "originalName") String originalName,
			@RequestParam(required = false, name = "width") Integer width,
			@RequestParam(required = false, name = "height") Integer height,
			@RequestParam(required = false, name = "inline") String inline, HttpServletRequest request) {
		D3EResourceHandler loadHandler = handlers.getOrDefault(getPrefix(fileName), null);
		if (loadHandler == null) {
			return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
		}

		Resource resource = null;
		try {
			if(fileName.startsWith("temp")) {
				resource = loadFileAsResource(loadHandler, fileName, 0, 0);
			}else {
				resource = loadFileAsResource(loadHandler, fileName, width == null ? 0 : width,
						height == null ? 0 : height);
			}
		} catch (Exception e) {
			if (width != null && height != null) {
				try {
					loadFileAsResource(loadHandler, fileName, 0, 0); // Just check original exists
					resizer.resizeNow(fileName, width, height, loadHandler);
					resource = loadFileAsResource(loadHandler, fileName, width, height);
				} catch (Exception e1) {
					return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
				}
			} else {
				return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
			}
		}

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		String headerFileName = (originalName != null && !originalName.isEmpty()) ? originalName
				: resource.getFilename();
		BodyBuilder builder = ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType));
		if (!Objects.equal(inline, "true")) {
			builder = builder.header(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=\"" + headerFileName + "\"");
		}
		return builder.header(HttpHeaders.CACHE_CONTROL, "max-age=" + TIMEOUT).body(resource);
	}

	private String getPrefix(String fileName) {
		int first = fileName.indexOf(':');
		if (first == -1) {
			return null;
		}
		return fileName.substring(0, first);
	}

	public Resource loadFileAsResource(D3EResourceHandler loadHandler, String fileName, int width, int height) {
		String resizedName = FileUtils.getResizedName(fileName, width, height);
		return loadHandler.get(resizedName);
	}
}