package Project.Restaurantbackend.entity;

import Project.Restaurantbackend.entity.templates.AbsEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BuyProduct extends AbsEntity {
    @ManyToMany
    private List<Product> products;

    @Column(nullable = false)
    private Integer size;

    @Column(nullable = false)
    private Double price;
}
