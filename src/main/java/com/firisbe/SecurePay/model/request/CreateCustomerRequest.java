package com.firisbe.SecurePay.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCustomerRequest {

    @NotBlank(message = "Name can not be blank!")
    private String customerName;

    @Email(message = "Invalid email format!")
    @NotBlank(message = "Email can not be blank!")
    private String email;
}
