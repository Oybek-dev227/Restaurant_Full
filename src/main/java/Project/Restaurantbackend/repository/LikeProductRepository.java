package Project.Restaurantbackend.repository;

import Project.Restaurantbackend.entity.LikeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface LikeProductRepository extends JpaRepository<LikeProduct, UUID> {
}
