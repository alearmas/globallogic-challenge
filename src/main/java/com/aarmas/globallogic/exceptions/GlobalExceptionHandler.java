package com.aarmas.globallogic.exceptions;

import com.aarmas.globallogic.entities.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Collections;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse.ErrorDetail errorDetail = new ErrorResponse.ErrorDetail(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage()
        );

        ErrorResponse errorResponse = new ErrorResponse(Collections.singletonList(errorDetail));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse.ErrorDetail errorDetail = new ErrorResponse.ErrorDetail(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );

        ErrorResponse errorResponse = new ErrorResponse(Collections.singletonList(errorDetail));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
