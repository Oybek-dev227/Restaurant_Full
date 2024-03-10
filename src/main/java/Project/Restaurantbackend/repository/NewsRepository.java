package Project.Restaurantbackend.repository;

import Project.Restaurantbackend.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NewsRepository extends JpaRepository<News, UUID> {
}
