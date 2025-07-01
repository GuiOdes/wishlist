package com.guiodes.wishlist.infra.api.handler;

import com.guiodes.wishlist.domain.exception.DuplicatedProductException;
import com.guiodes.wishlist.domain.exception.MaxSizeReachedException;
import com.guiodes.wishlist.infra.api.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    private final Logger logger = LogManager.getLogger(ControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception e,
            HttpServletRequest request
    ) {
        logger.error("An error occurred: {}", e.getMessage(), e);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponse error = new ErrorResponse(
                e.getMessage(),
                status.value(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(status)
                .body(error);
    }

    @ExceptionHandler(DuplicatedProductException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(
            DuplicatedProductException e,
            HttpServletRequest request
    ) {
        logger.warn("Conflict error: {}", e.getMessage(), e);

        HttpStatus status = HttpStatus.CONFLICT;

        ErrorResponse error = new ErrorResponse(
                e.getMessage(),
                status.value(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(status)
                .body(error);
    }

    @ExceptionHandler(MaxSizeReachedException.class)
    public ResponseEntity<ErrorResponse> handleMaxSizeReachedException(
            MaxSizeReachedException e,
            HttpServletRequest request
    ) {
        logger.warn("Max size reached error: {}", e.getMessage(), e);

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse error = new ErrorResponse(
                e.getMessage(),
                status.value(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(status)
                .body(error);
    }
}
