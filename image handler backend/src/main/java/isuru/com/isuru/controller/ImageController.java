package isuru.com.isuru.controller;

import isuru.com.isuru.model.Image;
import isuru.com.isuru.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*") // Adjust the origin as needed
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    // Upload image
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        Image image = imageService.uploadImage(file);
        return ResponseEntity.ok("Image uploaded successfully with ID: " + image.getId());
    }

    // View all images metadata
    @GetMapping
    public ResponseEntity<List<Image>> getAllImages() {
        return ResponseEntity.ok(imageService.getAllImages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getImageById(@PathVariable Long id) throws IOException {
        Image image = imageService.getImageById(id);
        File file = new File(image.getFilePath());

        Resource resource = new UrlResource(file.toURI());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }
}
