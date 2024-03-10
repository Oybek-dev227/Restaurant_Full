package Project.Restaurantbackend.service;

import Project.Restaurantbackend.entity.Comment;
import Project.Restaurantbackend.entity.User;
import Project.Restaurantbackend.exception.ResourceNotFoundException;
import Project.Restaurantbackend.implement.service.CommentServiceImpl;
import Project.Restaurantbackend.payload.ApiResponse;
import Project.Restaurantbackend.payload.CommentDto;
import Project.Restaurantbackend.repository.AuthRepository;
import Project.Restaurantbackend.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CommentService implements CommentServiceImpl {
    private final CommentRepository commentRepository;
    private final AuthRepository authRepository;

    @Override
    public List<CommentDto> getComment() {
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : commentRepository.findAll()) {
            CommentDto commentDto = CommentDto.builder()
                    .id(comment.getId())
                    .user(comment.getUser())
                    .date(String.valueOf(comment.getCreatedAt()))
                    .message(comment.getMessage())
                    .build();
            commentDtos.add(commentDto);
        }
        return commentDtos;
    }

    @Override
    public ApiResponse<?> addComment(CommentDto commentDto) {
        try {
            User user = authRepository.findById(commentDto.getUserId()).orElseThrow(() -> new ResourceNotFoundException(404, "getUser", "userId", commentDto.getUserId()));
            Comment comment = Comment.builder()
                    .user(user)
                    .message(commentDto.getMessage())
                    .build();
            commentRepository.save(comment);
            return new ApiResponse<>("comment saqlandi", true, comment.getId());
        }catch (Exception e){
            return new ApiResponse<>("Comment saqlashda xatolik", false);
        }
    }
}
