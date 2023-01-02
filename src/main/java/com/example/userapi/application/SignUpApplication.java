package com.example.userapi.application;


import com.example.userapi.client.MailGunClient;
import com.example.userapi.client.mailgun.SendMailForm;
import com.example.userapi.domain.SignUpForm;
import com.example.userapi.domain.model.Customer;
import com.example.userapi.exception.CustomerException;
import com.example.userapi.exception.ErrorCode;
import com.example.userapi.service.SignUpCustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

// 1. 회원가입 폼 작성 및 전송(Client)
// 2. 서버에서 해당 데이터 저장 및 인증 코드 생성 후 이메일 발급(Server)
// 3. 전송 받은 이메일로 인증 시도(with 인증 코드)(Client)
// 4. 검증 후 인증 완료(Server)

@Slf4j
@Service
@RequiredArgsConstructor

public class SignUpApplication {


    private final MailGunClient mailGunClient;
    private final SignUpCustomerService signUpCustomerService;

    public String customerSignUp(SignUpForm form) {

        if (signUpCustomerService.isEmailExist(form.getEmail())) {
            throw new CustomerException(ErrorCode.ALREADY_REGISTER_USER);
        } else {

            Customer c = signUpCustomerService.signUp(form);
            String code = getRandomCode();
            LocalDateTime now = LocalDateTime.now();

            mailGunClient.sendEmail(
                    SendMailForm.builder()
                .from("test@Donkuen.com")
                .to(form.getEmail())
                .subject("Verification Email!")
                .text(getVerificationEmailBody(form.getEmail(), form.getName(), code))
                .build());

            signUpCustomerService.ChangeCustomerValidateEmail(c.getId(), code);
        }

        return "성공적으로 회원가입이 되었습니다.";
    }

    // apache common-lang03
    private String getRandomCode() {

        return RandomStringUtils.random(10, true, true);
    }


    // 인증 요구 링크 생성 메소드
    private String getVerificationEmailBody(String email, String name, String code) {

        StringBuilder sb = new StringBuilder();

        return sb.append("Hi there ").append(name).append("! Please Click Link for verification.\n\n")
                 .append("http://localhost:8081/signup/verify/customer?email=")
                 .append(email)
                 .append("&code=")
                 .append(code).toString();
    }

    // 이메일 인증 관련 메소드
    public void customerVerify(String email, String code) {
        signUpCustomerService.verifyEmail(email,code);

    }
}
