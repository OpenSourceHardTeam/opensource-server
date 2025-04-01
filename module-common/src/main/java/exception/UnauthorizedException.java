package exception;

import org.springframework.http.HttpStatus;
import type.ErrorType;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException(ErrorType errorType) {
        super(errorType, HttpStatus.UNAUTHORIZED);
    }
}
