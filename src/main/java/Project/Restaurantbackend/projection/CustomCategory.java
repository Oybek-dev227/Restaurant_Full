package Project.Restaurantbackend.projection;

import Project.Restaurantbackend.entity.Category;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "customCategory",types = Category.class)
public interface CustomCategory {

    Integer getId();
    String getName();
}
