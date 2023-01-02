package com.example.userapi.controller;


import com.example.userapi.application.SignUpApplication;
import com.example.userapi.domain.SignUpForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RequiredArgsConstructor
@RequestMapping("/signup")
@RestController

public class SignUpController {
    private final SignUpApplication signUpApplication;

    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody SignUpForm form) {
        return ResponseEntity.ok(
                signUpApplication.customerSignUp(form)
        );
    }


    @PutMapping("/verify/customer")
    public ResponseEntity<?> verifyCustomer(String email, String code) {

        signUpApplication.customerVerify(email,code);
        return ResponseEntity.ok("인증이 완료되었습니다.");
    }
}
