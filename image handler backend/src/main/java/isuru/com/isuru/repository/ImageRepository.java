package isuru.com.isuru.repository;


import isuru.com.isuru.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}