package movies.exception.result;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum MovieExceptionResult {

    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글정보를 찾을 수 없습니다"),
    CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다"),
    MOVIE_NOT_FOUND(HttpStatus.NOT_FOUND, "영화정보를 찾을 수 없습니다"),
    ;

    private final HttpStatus status;
    private final String message;

    }
