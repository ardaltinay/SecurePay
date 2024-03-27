package com.firisbe.SecurePay.dto;

import com.firisbe.SecurePay.entity.CreditCard;
import com.firisbe.SecurePay.entity.Payment;

import java.util.Set;

public record CustomerDto(Long id, String email, Set<CreditCard> creditCards, Set<Payment> payments) {
}
