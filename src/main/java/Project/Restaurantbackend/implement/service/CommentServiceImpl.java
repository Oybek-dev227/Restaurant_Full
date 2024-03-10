package Project.Restaurantbackend.implement.service;

import Project.Restaurantbackend.payload.ApiResponse;
import Project.Restaurantbackend.payload.CommentDto;

import java.util.List;
import java.util.UUID;

public interface CommentServiceImpl {
    List<CommentDto> getComment();
    ApiResponse<?> addComment(CommentDto commentDto);
}
