package Project.Restaurantbackend.repository;

import Project.Restaurantbackend.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BasketRepository extends JpaRepository<Basket, UUID> {
}
