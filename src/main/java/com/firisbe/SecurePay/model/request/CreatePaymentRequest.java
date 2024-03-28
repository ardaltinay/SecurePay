package com.firisbe.SecurePay.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePaymentRequest {

    @NotNull(message = "Customer id is required!")
    private Long customerId;

    @NotNull(message = "Credit card id is required!")
    private Long creditCardId;

    @Positive(message = "Amount must be greater than 0!")
    private BigDecimal amount;
}
