package com.rightpair.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorMessage> handleBusinessException(BusinessException e) {
        return ResponseEntity.badRequest()
                .body(ErrorMessage.create(e.errorCode, e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleBindValidationError(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest()
                .body(ErrorMessage.create(ErrorCode.INVALID_VALIDATION, e.getMessage()));
    }
}
