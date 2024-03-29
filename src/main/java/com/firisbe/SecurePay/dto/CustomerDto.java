package com.firisbe.SecurePay.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firisbe.SecurePay.entity.CreditCard;
import com.firisbe.SecurePay.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CustomerDto {

    private Long id;
    private String email;
    private String name;
    private Set<CreditCard> creditCards;
    @JsonIgnore
    private Set<Payment> payments;
}
