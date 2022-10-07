package movies.service.Impl;

import movies.domain.Movie;
import movies.dto.CommentRequestDto;
import movies.domain.Account;
import movies.domain.Comment;
import movies.repository.AccountRepository;
import movies.repository.MovieRepository;
import movies.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl {

    private final AccountRepository accountRepository;
    private final MovieRepository movieRepository;
    private final CommentRepository commentRepository;


    public HashMap<String, Object> ReadComment(Long movie_id){
        float totalStarRate = 0;
        float starRate;
        float avgStarRate;
        List<Comment> comments = commentRepository.findByMovieIdOrderByCreatedAtDesc(movie_id);
        List<Comment> starRateCountList = commentRepository.findByMovieId(movie_id);

        HashMap<String, Object> map = new HashMap<>();
        Integer starRateCount = starRateCountList.size();
        for(int i=0; i<starRateCountList.size(); i++){
            totalStarRate += starRateCountList.get(i).getStarRate();
            System.out.println(totalStarRate);
        }
        starRate = totalStarRate / starRateCount;
        System.out.println(starRate);
        avgStarRate= Float.parseFloat(String.format("%.1f", starRate));
        map.put("comment", comments);
        map.put("avgStarRate", avgStarRate);
        map.put("starRateCount", starRateCount);

        return map;
    }

    public Comment CreateComment(CommentRequestDto requestDto, Long movie_id, Long account_id){
        Movie movie = movieRepository.findById(movie_id).orElseThrow(
                () -> new IllegalArgumentException("영화가 존재하지 않습니다.")
        );
        Account account = accountRepository.findById(account_id).orElseThrow(
                () -> new IllegalArgumentException("계정이 존재하지 않습니다.")
        );
        Comment checkComment = commentRepository.findByAccountIdAndMovieId(account_id, movie_id);

        if (checkComment == null){
            Comment comment = new Comment(requestDto);
            movie.addComment(comment);
            account.addComment(comment);
            commentRepository.save(comment);

            return comment;
        }else{
            return null;
        }
    }

    @Transactional
    public Comment UpdateComment(CommentRequestDto requestDto, Long comment_id, Long account_id) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if (!comment.getAccount().getId().equals(account_id)){
            return null;
        }else{
            comment.updateComment(requestDto);
        }
        return comment;
    }

    public Comment DeleteComment(Long movie_id, Long comment_id, Long account_id){
        Movie movie = movieRepository.findById(movie_id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Comment comment = commentRepository.findById(comment_id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        Account account = accountRepository.findById(account_id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if (!comment.getAccount().getId().equals(account_id)){
            return null;
        }else{
            movie.deleteComment(comment);
            account.deleteComment(comment);
            commentRepository.deleteById(comment_id);
            return comment;
        }

    }
}
