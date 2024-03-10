package Project.Restaurantbackend.entity;

import Project.Restaurantbackend.entity.templates.AbsEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "products")
public class Product extends AbsEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 300)
    private String description;

    @Column(nullable = false)
    private Double price;

    private boolean isLike;

    private boolean isBasket;

    private Integer stars;

    private boolean sale = false;

    private double salePercent;

    private double salePrice;

    @ManyToMany
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Photo> photos;

    private UUID photoId;

    @ManyToOne
    private Category category;

}
