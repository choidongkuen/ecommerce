package com.example.userapi.client;


import com.example.userapi.client.mailgun.SendMailForm;
import feign.Response;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "mailgun", url = "https://api.mailgun.net/v3/")
@Qualifier("mailgun")

public interface MailGunClient {

    @PostMapping("sandbox92a3501de81b4fd894cc45108c2e5e8a.mailgun.org/messages")
    Response sendEmail(@SpringQueryMap SendMailForm form);
}
