package Project.Restaurantbackend.entity;

import Project.Restaurantbackend.entity.templates.AbsNameEntity;
import lombok.*;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class Category extends AbsNameEntity {
}
