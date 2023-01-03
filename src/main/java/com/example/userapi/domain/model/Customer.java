package com.example.userapi.domain.model;


import com.example.userapi.domain.SignUpForm;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
@AuditOverride(forClass = BaseEntity.class)
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;

    private String password;

    private LocalDate birth;

    private String phone;

    private LocalDateTime verifyExpiredAt;

    private String verificationCode;

    private boolean verified;

    private Long balance;

    public static Customer from(SignUpForm form) {

        return Customer.builder()
                .email(form.getEmail())
                .name(form.getName())
                .password(form.getPassword())
                .birth(form.getBirth())
                .phone(form.getPhone())
                .verified(false)
                .build();
    }
}
