package Project.Restaurantbackend.repository;



import Project.Restaurantbackend.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, UUID> {
    AttachmentContent findByAttachmentId(UUID id);
}
