package com.firisbe.SecurePay.controller;

import com.firisbe.SecurePay.model.request.CreateCustomerRequest;
import com.firisbe.SecurePay.model.request.UpdateCreditCardInfoRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {


    @PostMapping
    public ResponseEntity<?> addCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{customerId}/credit-card")
    public ResponseEntity<?> updateCreditCardInfoByCustomerId(@PathVariable Long customerId, @Valid @RequestBody UpdateCreditCardInfoRequest request) {
        return ResponseEntity.ok().build();
    }





}
