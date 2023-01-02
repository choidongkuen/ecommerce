package com.example.userapi.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@Builder
@Getter
@Setter

public class SignUpForm {

    private String email;

    private String name;

    private String password;

    private LocalDate birth;

    private String phone;
}
