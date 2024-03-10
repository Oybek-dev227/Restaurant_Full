package Project.Restaurantbackend.payload;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsDto {
    private UUID id;
    private UUID photoId;
    private String name;
    private String description;
}
