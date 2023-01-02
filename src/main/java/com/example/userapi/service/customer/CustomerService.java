package com.example.userapi.service.customer;

import com.example.userapi.domain.model.Customer;
import com.example.userapi.repository.CustomerRepository;
import com.example.userapi.exception.CustomerException;
import com.example.userapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor

public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer findValidCustomer(String email, String password) {

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomerException(ErrorCode.USER_NOT_FOUND));

        if(!Objects.equals(customer.getPassword(), password)) {
            throw new CustomerException(ErrorCode.PASSWORD_UN_MATCH);
        }

        if(!customer.isVerified()) {
            throw new CustomerException(ErrorCode.NOT_VERIFIED);
        }

        return customer;
    }
}
