package com.example.userapi.application;


import com.example.userapi.common.UserType;
import com.example.userapi.config.JwtAuthenticationProvider;
import com.example.userapi.domain.SignInForm;
import com.example.userapi.domain.model.Customer;
import com.example.userapi.domain.model.Seller;
import com.example.userapi.service.customer.CustomerService;
import com.example.userapi.service.seller.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SignInApplication {
    private final CustomerService customerService;
    private final SellerService sellerService;
    private final JwtAuthenticationProvider provider;
    public String customerLogin(SignInForm form){

        // 로그인 가능 여부
        Customer customer = customerService.findValidCustomer(form.getEmail(), form.getPassword());
        // 토큰 발행
        return provider.createToken(customer.getEmail(), customer.getId(), UserType.CUSTOMER);
    }

    public String sellerLogin(SignInForm form){
        Seller seller = sellerService.findValidSeller(form.getEmail(), form.getPassword());
        return provider.createToken(seller.getEmail(), seller.getId(), UserType.SELLER);
    }

}