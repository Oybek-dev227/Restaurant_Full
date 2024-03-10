package Project.Restaurantbackend.payload;

import Project.Restaurantbackend.entity.Product;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketDto {
    private UUID id;
    private List<Product> products;
    private UUID productId;
}
