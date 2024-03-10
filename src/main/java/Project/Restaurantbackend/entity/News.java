package Project.Restaurantbackend.entity;

import Project.Restaurantbackend.entity.templates.AbsEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class News extends AbsEntity {
    @Column(nullable = false)
    private UUID photoId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;
}
