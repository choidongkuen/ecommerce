package com.example.userapi.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Slf4j
@Builder
@Getter
@Setter

public class SignUpForm {


    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "올바른 이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 4, max = 20)
    private String password;

    @NotBlank(message = "생일은 필수 입력값입니다.")
    private LocalDate birth;

    @NotBlank(message = "전화번호는 필수 입력값입니다.")
    private String phone;
}
