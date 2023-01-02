package com.example.userapi.service.customer;


import com.example.userapi.domain.SignUpForm;
import com.example.userapi.domain.model.Customer;
import com.example.userapi.repository.CustomerRepository;
import com.example.userapi.exception.CustomerException;
import com.example.userapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor

public class SignUpCustomerService {

    private final CustomerRepository customerRepository;

    public Customer signUp(SignUpForm form) {

        return customerRepository.save(Customer.from(form));

    }
    public boolean isEmailExist(String email) {

        return customerRepository.findByEmail(email.toLowerCase(Locale.ROOT))
                                 .isPresent();
    }

    public void verifyEmail(String email, String code) {

        Customer customer = customerRepository.findByEmail(email)
                          .orElseThrow(() -> new CustomerException(ErrorCode.USER_NOT_FOUND));


        verifyEmail(code, customer);


    }

    // 이메일 인증에 대한 메소드
    private static void verifyEmail(String code, Customer customer) {
        // 이미 인증 된 경유
        if(customer.isVerified()){
            throw new CustomerException(ErrorCode.ALREADY_VERIFIED);
        }


        // 발급한 인증코드가 다른 경우
        if(!customer.getVerificationCode().equals(code)) {
            throw new CustomerException(ErrorCode.WRONG_VERIFICATION);
        }


        // 이미 인증코드의 기한이 지난 경우
        if(customer.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomerException(ErrorCode.EXPIRED_CODE);
        }
    }

    // 이메일 인증 후 인증 필드 처리 메소드
    @Transactional
    public LocalDateTime ChangeCustomerValidateEmail(Long customerId,String verificationCode) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new CustomerException(ErrorCode.USER_NOT_FOUND)
                );

        customer.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
        customer.setVerificationCode(verificationCode);

        return customer.getVerifyExpiredAt();
    }
}
