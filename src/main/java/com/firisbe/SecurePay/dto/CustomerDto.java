package com.firisbe.SecurePay.dto;

import com.firisbe.SecurePay.entity.CreditCard;
import com.firisbe.SecurePay.entity.Payment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class CustomerDto {

    private Long id;
    private String email;
    private String name;
    private Set<CreditCard> creditCards;
    private Set<Payment> payments;
}
