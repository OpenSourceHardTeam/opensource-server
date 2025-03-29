package exception;

import org.springframework.http.HttpStatus;
import type.ErrorType;

public class NotFoundException extends BaseException {
    public NotFoundException(ErrorType errorType) {
        super(errorType, HttpStatus.NOT_FOUND);
    }
}
