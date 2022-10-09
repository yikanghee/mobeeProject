package movies.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import movies.exception.result.MovieExceptionResult;

@Getter
@RequiredArgsConstructor
public class MovieException extends RuntimeException{

    private final MovieExceptionResult commentExceptionResult;
}
