package movies.controller;

import movies.dto.CommentRequestDto;
import movies.service.CommentService;
import movies.config.UserDetailsImpl;
import movies.domain.Comment;
import movies.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/api/movies/{movie_id}/comments")
    public HashMap<String, Object> ReadComment(@PathVariable Long movie_id){
        return commentService.ReadComment(movie_id);
    }

    @PostMapping("/api/movies/{movie_id}/comments")
    public ResponseEntity CreateComment(@RequestBody CommentRequestDto requestDto, @PathVariable Long movie_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Comment comment = commentService.CreateComment(requestDto, movie_id, userDetails.getAccount().getId());
        if (comment == null){
            Message message = Message.builder()
                    .message1("댓글은 한 책에 한번만 작성가능합니다.")
                    .build();
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/movies/{movie_id}/comments/{comment_id}")
    public ResponseEntity UpdateComment(@RequestBody CommentRequestDto requestDto, @PathVariable Long movie_id, @PathVariable Long comment_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Comment comment = commentService.UpdateComment(requestDto, comment_id, userDetails.getAccount().getId());
        if (comment == null){
            Message message = Message.builder()
                    .message1("직접 작성한 댓글만 수정할 수 있습니다.")
                    .build();
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/movies/{movie_id}/comments/{comment_id}")
    public ResponseEntity DeleteComment(@PathVariable Long movie_id, @PathVariable Long comment_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Comment comment = commentService.DeleteComment(movie_id, comment_id, userDetails.getAccount().getId());
        if (comment == null){
            Message message = Message.builder()
                    .message1("직접 작성한 댓글만 삭제할 수 있습니다.")
                    .build();
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }
}
