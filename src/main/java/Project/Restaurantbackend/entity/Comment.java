package Project.Restaurantbackend.entity;

import Project.Restaurantbackend.entity.templates.AbsEntity;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Comment extends AbsEntity {
    @OneToOne
    private User user;

//    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String message;
}
