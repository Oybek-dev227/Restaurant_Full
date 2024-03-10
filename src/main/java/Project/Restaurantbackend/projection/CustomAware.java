package Project.Restaurantbackend.projection;

import Project.Restaurantbackend.entity.Aware;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "customAware",types = Aware.class)
public interface CustomAware {

    Integer getId();
    String getName();
    String getLink();
}
