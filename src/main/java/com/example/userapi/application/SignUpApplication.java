package com.example.userapi.application;


import com.example.userapi.client.MailGunClient;
import com.example.userapi.client.mailgun.SendMailForm;
import com.example.userapi.domain.SignUpForm;
import com.example.userapi.domain.model.Customer;
import com.example.userapi.domain.model.Seller;
import com.example.userapi.exception.CustomerException;
import com.example.userapi.exception.ErrorCode;
import com.example.userapi.service.customer.SignUpCustomerService;
import com.example.userapi.service.seller.SellerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

// 1. 회원가입 폼 작성 및 전송(Client)
// 2. 서버에서 해당 데이터 저장 및 인증 코드 생성 후 이메일 발급(Server)
// 3. 전송 받은 이메일로 인증 시도(with 인증 코드)(Client)
// 4. 검증 후 인증 완료(Server)

@Service
@RequiredArgsConstructor
public class SignUpApplication {

    private final MailGunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;
    private final SellerService sellerService;

    public void customerVerify(String email, String code){
        signUpCustomerService.verifyEmail(email, code);
    }

    public String customerSignUp(SignUpForm form) {
        if (signUpCustomerService.isEmailExist(form.getEmail())) {
            throw new CustomerException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            Customer customer = signUpCustomerService.signUp(form);

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                                                    .from("tester@zb.com")
                                                    .to("magi8520@gmail.com")
                                                    .subject("verification Email!")
                                                    .text(getVerificationEmailBody(form.getEmail(), form.getName(), "customer", code))
                                                    .build();
            mailgunClient.sendEmail(sendMailForm);

            return "회원 가입에 성공하였습니다. " + signUpCustomerService.ChangeCustomerValidateEmail(
                    customer.getId(), code);
        }
    }

    public void sellerVerify(String email, String code){
        sellerService.verifyEmail(email, code);
    }

    public String sellerSignUp(SignUpForm form) {
        if (sellerService.isEmailExist(form.getEmail())) {
            throw new CustomerException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            Seller seller = sellerService.signUp(form);

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                                                    .from("tester@zb.com")
                                                    .to("magi8520@gmail.com")
                                                    .subject("verification Email!")
                                                    .text(getVerificationEmailBody(form.getEmail(), form.getName(), "seller", code))
                                                    .build();
            mailgunClient.sendEmail(sendMailForm);

            return "회원 가입에 성공하였습니다. " + sellerService.changeSellerValidateEmail(
                    seller.getId(), code);
        }
    }

    private String getRandomCode() {
        return RandomStringUtils.random(10, true, true);
    }

    private String getVerificationEmailBody(String email, String name, String type, String code) {
        StringBuilder sb = new StringBuilder();
        return sb.append("Hello ").append(name)
                 .append("! Please click this link for verification\n\n")
                 .append("http://localhost:8081/signup/"+ type + "/verify/?email=")
                 .append(email)
                 .append("&code=")
                 .append(code)
                 .toString();
    }

}