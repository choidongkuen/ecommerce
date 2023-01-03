package com.example.userapi.controller;


import com.example.userapi.common.UserVo;
import com.example.userapi.config.JwtAuthenticationProvider;
import com.example.userapi.domain.customer.CustomerDto;
import com.example.userapi.domain.model.Customer;
import com.example.userapi.exception.CustomerException;
import com.example.userapi.exception.ErrorCode;
import com.example.userapi.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/customer")
@RestController
public class CustomerController {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final CustomerService customerService;

    @GetMapping("/getInfo")
    public ResponseEntity<?> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {


        UserVo vo = jwtAuthenticationProvider.getUserVo(token);
        Customer c = customerService.findByIdAndEmail(vo.getId(), vo.getEmail())
                .orElseThrow(
                        ()-> new CustomerException(ErrorCode.USER_NOT_FOUND)
                );

        return ResponseEntity.ok(CustomerDto.from(c));
    }
}
