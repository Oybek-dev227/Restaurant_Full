package Project.Restaurantbackend.repository.res;

import Project.Restaurantbackend.entity.Aware;
import Project.Restaurantbackend.projection.CustomAware;
import Project.Restaurantbackend.projection.CustomCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "list",path = "aware",excerptProjection = CustomAware.class)
@CrossOrigin
public interface AwareCategory extends JpaRepository<Aware,Integer> {
}
