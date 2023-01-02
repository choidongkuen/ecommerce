package com.example.userapi.application;


import com.example.userapi.client.MailGunClient;
import com.example.userapi.service.SignUpCustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor

public class SignUpApplication {


    private final MailGunClient mailGunClient;

    private final SignUpCustomerService signUpCustomerService;
}
