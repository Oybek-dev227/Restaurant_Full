package Project.Restaurantbackend.payload;

import Project.Restaurantbackend.entity.Category;
import Project.Restaurantbackend.entity.Comment;
import Project.Restaurantbackend.entity.Photo;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private UUID id;
    private String name;
    private Integer categoryId;
    private Category category;
    private String description;
    private Double price;
    private boolean isLike;
    private boolean isBasket;
    private Integer stars;
    private boolean sale;
    private double salePercent;
    private List<Comment> comments;
    private UUID commentId;
    private List<Photo> photos;
    private UUID photoId;
    private String malumot;
}
