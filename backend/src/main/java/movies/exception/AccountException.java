package movies.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import movies.exception.result.AccountExceptionResult;

@RequiredArgsConstructor
@Getter
public class AccountException extends RuntimeException {

    private final AccountExceptionResult exceptionResult;

}
