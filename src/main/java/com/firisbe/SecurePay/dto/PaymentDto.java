package com.firisbe.SecurePay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
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
