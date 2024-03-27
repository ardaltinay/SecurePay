package com.firisbe.SecurePay.dto;

import java.util.Set;

public record CustomerDto(Long id, String email, String name, Set<CreditCardDto> creditCards, Set<PaymentDto> payments) {
}
