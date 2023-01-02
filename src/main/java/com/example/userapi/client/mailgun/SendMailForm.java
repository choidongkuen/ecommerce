package com.example.userapi.client.mailgun;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SendMailForm {

    public String from;
    public String to;
    public String subject;
    public String text;

}
