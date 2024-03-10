package Project.Restaurantbackend.payload;

import Project.Restaurantbackend.entity.Product;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeProductDto {
    private UUID id;
    private List<Product> products;
    private Product product;
    private UUID productId;
    private boolean isLike;
}
