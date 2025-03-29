package exception;

import org.springframework.http.HttpStatus;
import type.ErrorType;

public class BadRequestException extends BaseException {
    public BadRequestException(ErrorType errorType) {
        super(errorType, HttpStatus.BAD_REQUEST);
    }
}
