package exception;

import org.springframework.http.HttpStatus;
import type.ErrorType;

public class ForbiddenException extends BaseException {
    public ForbiddenException(ErrorType errorType) {
        super(errorType, HttpStatus.FORBIDDEN);
    }
}
