package com.firisbe.SecurePay.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class PaymentDto {

    private Long id;
    private BigDecimal amount;
    private LocalDate createdAt;
    private String customerName;
    private String customerEmail;
    private String creditCardNumber;
    private Long creditCardCvvNumber;
    private LocalDate creditCardExpireDate;
}
