package Project.Restaurantbackend.controller;

import Project.Restaurantbackend.entity.Comment;
import Project.Restaurantbackend.exception.ResourceNotFoundException;
import Project.Restaurantbackend.implement.controller.CommentControllerImpl;
import Project.Restaurantbackend.payload.ApiResponse;
import Project.Restaurantbackend.payload.CommentDto;
import Project.Restaurantbackend.repository.AuthRepository;
import Project.Restaurantbackend.repository.CommentRepository;
import Project.Restaurantbackend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController implements CommentControllerImpl {
    private final CommentRepository commentRepository;
    private final AuthRepository authRepository;
    private final CommentService commentService;

    @Override
    @GetMapping
    public HttpEntity<?> getComment() {
        List<CommentDto> comment = commentService.getComment();
        return ResponseEntity.ok(comment);
    }

    @Override
    @PostMapping
    public HttpEntity<?> addComment(@RequestBody CommentDto commentDto) {
        ApiResponse<?> addComment = commentService.addComment(commentDto);
        return ResponseEntity.status(addComment.isSuccess() ? 200 : 409).body(addComment);
    }
}
