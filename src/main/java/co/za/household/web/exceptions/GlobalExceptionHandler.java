package co.za.household.web.exceptions;

import co.za.household.domain.exceptions.BadRequestException;
import co.za.household.domain.exceptions.InternalServerErrorException;
import co.za.household.domain.exceptions.NotFoundException;
import co.za.household.domain.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(InternalServerErrorException ex) {
        log.error("Internal server error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse()
                        .setStatusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .setMessage(ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse()
                        .setStatusCode(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .setMessage(ex.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        log.warn("Bad request: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse()
                        .setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .setMessage(ex.getMessage()));
    }
}


