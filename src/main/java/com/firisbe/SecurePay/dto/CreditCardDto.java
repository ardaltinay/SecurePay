package com.firisbe.SecurePay.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CreditCardDto {

    private Long id;
    private String cardNumber;
    private Long cvvNumber;
    private LocalDate expireDate;
}
