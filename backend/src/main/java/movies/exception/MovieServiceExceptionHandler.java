package movies.exception;

import lombok.extern.slf4j.Slf4j;
import movies.exception.result.AccountExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class MovieServiceExceptionHandler extends ResponseEntityExceptionHandler {

    // Catch AccountException
    @ExceptionHandler({AccountException.class})
    public ResponseEntity<ErrorResponse> handleAccountException(final AccountException exception) {
        log.warn("AccountException occur : ", exception);
        return this.makeErrorResponseEntity(exception.getExceptionResult());
    }

    /**
     * Exception 정보에서 ResponseEntity(ErrorResponse) 생성
     * -> 각 Exception에 맞에 Param 변경
     *
     * @param exceptionResult
     * @return
     */
    private ResponseEntity<ErrorResponse> makeErrorResponseEntity(final AccountExceptionResult exceptionResult) {
        return ResponseEntity.status(exceptionResult.getStatus())
                .body(new ErrorResponse(exceptionResult.name(), exceptionResult.getMessage()));
    }

}
