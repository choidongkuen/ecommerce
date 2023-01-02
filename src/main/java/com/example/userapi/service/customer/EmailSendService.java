package com.example.userapi.service.customer;

import com.example.userapi.client.MailGunClient;
import com.example.userapi.client.mailgun.SendMailForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor

public class EmailSendService {

    private final MailGunClient mailGunClient;

    public String sendEmail() {

        SendMailForm form = SendMailForm.builder()
                .from("danaver13@gmail.com")
                .to("danaver12@daum.net")
                .subject("Test Email from zero-base")
                .text("test!")
                .build();

        return mailGunClient.sendEmail(form).getBody();
    }
}
