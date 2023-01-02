package com.example.userapi.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j

public class ExceptionController {

    @ExceptionHandler({CustomerException.class})

    public ResponseEntity<ExceptionResponse> customRequestException(CustomerException e) {
        log.warn("api Exception : {}",e.getErrorCode());

        return ResponseEntity.badRequest().body(
                new ExceptionResponse(e.getMessage(),e.getErrorCode())
        );
    }
}
