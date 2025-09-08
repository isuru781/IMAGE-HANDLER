package isuru.com.isuru.service;

import isuru.com.isuru.model.Image;
import isuru.com.isuru.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    @Value("${app.upload.dir}")
    private String uploadDirConfig;

    /**
     * Handles the upload of an image file.
     * <p>
     * Steps:
     * 1. Resolves the absolute path for the uploads directory using the configured property (app.upload.dir).
     * 2. Ensures the uploads directory exists, creating it if necessary.
     * 3. Generates a unique file name using UUID to avoid collisions.
     * 4. Saves the uploaded file to the uploads directory.
     * 5. Creates and persists an Image entity with metadata about the file.
     *
     * @param file the uploaded image file
     * @return the saved Image entity containing metadata
     * @throws IOException if the upload directory cannot be created or file saving fails
     */
// Service method to handle image upload
    public Image uploadImage(MultipartFile file) throws IOException {
        // Step 1: Resolve absolute path for uploads directory
        // System.getProperty("user.dir") -> returns the root folder where the Spring Boot app is running
        // File.separator -> adds the correct slash based on OS (Windows uses "\" , Linux/Mac uses "/")
        // uploadDirConfig -> folder name for storing images (probably defined in application.properties)
        String absoluteUploadDir = System.getProperty("user.dir") + File.separator + uploadDirConfig;

        // Create a File object representing the uploads directory path
        File directory = new File(absoluteUploadDir);

        // Step 2: Ensure the uploads directory actually exists
        // If the folder doesn't exist yet, create it (including all parent directories if needed)
        if (!directory.exists()) {
            boolean created = directory.mkdirs(); // mkdirs() creates the folder structure recursively
            if (!created) {
                // If creation failed, throw an IOException to stop the upload process
                throw new IOException("Failed to create upload directory");
            }
        }

        // Step 3: Generate a unique file name
        // This avoids overwriting files with the same original name
        // UUID.randomUUID() -> generates a random unique string
        // file.getOriginalFilename() -> gets the original name of the uploaded file (like "photo.png")
        // Example result: "uploads/123e4567-e89b-12d3-a456-426614174000_photo.png"
        String filePath = absoluteUploadDir + File.separator + UUID.randomUUID() + "_" + file.getOriginalFilename();

        // Create a File object pointing to the target file path
        File dest = new File(filePath);

        // Step 4: Save the uploaded file physically on the server
        // transferTo() copies the uploaded content into our destination file
        file.transferTo(dest);

        // Step 5: Create an Image entity to store metadata in the database
        // - fileName: original file name from the client
        // - contentType: MIME type (like "image/png", "image/jpeg")
        // - filePath: absolute path in the server's file system where the image is saved
        Image image = Image.builder()
                .fileName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .filePath(filePath)
                .build();

        // Save metadata into the database (imageRepository handles persistence via JPA/Hibernate)
        return imageRepository.save(image);
    }


    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow();
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }
}
