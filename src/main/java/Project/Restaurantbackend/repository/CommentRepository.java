package Project.Restaurantbackend.repository;

import Project.Restaurantbackend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

}
