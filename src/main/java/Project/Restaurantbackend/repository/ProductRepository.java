package Project.Restaurantbackend.repository;

import Project.Restaurantbackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.UUID;
public interface ProductRepository extends JpaRepository<Product, UUID> {
    boolean existsProductByName(String name);
    @Query("select p from products p where p.category.id=?1")
    List<Product> getProductByCategory(Integer id);


}
