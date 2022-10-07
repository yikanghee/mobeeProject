package movies.service;

import movies.domain.Comment;
import movies.dto.CommentRequestDto;

import java.util.HashMap;

public interface CommentService {


    HashMap<String, Object> ReadComment(Long movie_id);

    Comment CreateComment(CommentRequestDto requestDto, Long movie_id, Long account_id);

    Comment UpdateComment(CommentRequestDto requestDto, Long comment_id, Long account_id);

    Comment DeleteComment(Long movie_id, Long comment_id, Long account_id);


}
