package Project.Restaurantbackend.repository;

import Project.Restaurantbackend.entity.BuyProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BuyProductRepository extends JpaRepository<BuyProduct, UUID> {
}
