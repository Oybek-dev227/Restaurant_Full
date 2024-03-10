package Project.Restaurantbackend.repository;

import Project.Restaurantbackend.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhotoRepository extends JpaRepository<Photo, UUID> {
    Photo findPhotoByPhotoId(UUID photoId);
}
