package net.brightlizard.spectrum.rest.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author Ovcharov Ilya (ovcharov.ilya@gmail.com)
 * net.brightlizard (c)
 */
/**
 * TODO: заменить на ControllerAdvice
 * */
@Component
public class ErrorHandler {

    public ResponseEntity get500ErrorResponseEntity(Exception e) {
        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                e.getMessage()
        );
        return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
