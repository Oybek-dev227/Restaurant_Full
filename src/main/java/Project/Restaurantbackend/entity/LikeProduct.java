package Project.Restaurantbackend.entity;

import Project.Restaurantbackend.entity.templates.AbsEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class LikeProduct extends AbsEntity {
    @OneToMany
    private List<Product> products;
}
