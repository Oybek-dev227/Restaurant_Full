package Project.Restaurantbackend.payload;

import Project.Restaurantbackend.entity.User;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private UUID id;
    private User user;
    private UUID userId;
    private String date;
    private String message;
}
