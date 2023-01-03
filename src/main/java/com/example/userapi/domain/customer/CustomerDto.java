package com.example.userapi.domain.customer;


import com.example.userapi.domain.model.Customer;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class CustomerDto {

    private Long id;

    private String email;

    public static CustomerDto from(Customer customer) {

        return CustomerDto.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .build();
    }
}
