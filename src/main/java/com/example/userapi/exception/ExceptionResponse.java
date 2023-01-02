package com.example.userapi.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponse {

    private String message;

    private ErrorCode errorCode;

}
