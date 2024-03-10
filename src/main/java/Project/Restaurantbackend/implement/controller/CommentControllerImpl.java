    package Project.Restaurantbackend.implement.controller;

import Project.Restaurantbackend.payload.CommentDto;
import org.springframework.http.HttpEntity;

import java.util.UUID;

public interface CommentControllerImpl {
    HttpEntity<?> getComment();
    HttpEntity<?> addComment(CommentDto commentDto);
}
