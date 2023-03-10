package com.example.userapi.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@RequiredArgsConstructor
@Getter

public enum ErrorCode {

    ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST,"해당 회원 정보가 없습니다." ),
    ALREADY_VERIFIED(HttpStatus.BAD_REQUEST, "이미 인증이 완료되었습니다."),
    WRONG_VERIFICATION(HttpStatus.BAD_REQUEST, "잘못된 인증 시도입니다."),
    EXPIRED_CODE(HttpStatus.BAD_REQUEST, "인증 기한이 지났습니다."),
    NOT_VERIFIED(HttpStatus.BAD_REQUEST, "해당 회원은 인증되지 않았습니다."),
    PASSWORD_UN_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    LOGIN_CHECK_FAIL(HttpStatus.BAD_REQUEST,"아이디 혹은 패스워드를 확인해주세요."),

    // BALANCE
    NOT_ENOUGH_BALANCE(HttpStatus.BAD_REQUEST, "잔액이 부족합니다."),
    EMAIL_DEST_NOT_MATCHED(HttpStatus.BAD_REQUEST, "보낼 이메일 주소를 확인해주세요.");


    private final HttpStatus httpStatus;
    private final String message;

}
