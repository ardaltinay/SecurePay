package com.firisbe.SecurePay.dto;

import java.time.LocalDate;

public record CreditCardDto(String encryptedCardNumber, Long cvvNumber, LocalDate expireDate) {
}
