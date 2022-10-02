package movies.exception.result;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AccountExceptionResult {

    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다"),
    ACCOUNT_SAME_ERROR(HttpStatus.CONFLICT, "회원 중복 에러"),
    ACCOUNT_OPTION_ERROR(HttpStatus.EXPECTATION_FAILED, "매개변수가 잘못된 형식으로 입력되었습니다"),
    ;

    private final HttpStatus status;
    private final String message;
}
