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
public class BuyProductDto {
    private UUID id;
    private List<Product> products;
    private UUID productId;
    private Integer size;
    private Double price;
}
