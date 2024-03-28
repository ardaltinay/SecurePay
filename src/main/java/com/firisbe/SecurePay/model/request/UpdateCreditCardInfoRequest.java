package com.firisbe.SecurePay.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UpdateCreditCardInfoRequest {

    @NotNull(message = "Customer id can not be null!")
    private Long customerId;

    @NotBlank(message = "Card number can not be blank!")
    private String cardNumber;

    @NotNull(message = "Cvv Number can not be null!")
    private Long cvvNumber;

    @NotNull(message = "Expire date information is required!")
    @FutureOrPresent(message = "Expire date must be future or present!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expireDate;
}
