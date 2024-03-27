package com.firisbe.SecurePay.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {


    @PostMapping
    public ResponseEntity<?> addPayment() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPayments(@RequestParam(required = false) String cardNumber,
                                            @RequestParam(required = false) String customerNumber,
                                            @RequestParam(required = false) String customerName) {
        return ResponseEntity.ok().build();
    }





}
