package Project.Restaurantbackend.entity;

import Project.Restaurantbackend.entity.templates.AbsEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
public class AttachmentContent extends AbsEntity {
    @OneToOne
    private Attachment attachment;

    private byte[] bytes;
}
