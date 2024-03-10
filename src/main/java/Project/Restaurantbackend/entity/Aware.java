package Project.Restaurantbackend.entity;

import Project.Restaurantbackend.entity.templates.AbsNameEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Aware extends AbsNameEntity {
    @Column(nullable = false)
    private String link;
}
