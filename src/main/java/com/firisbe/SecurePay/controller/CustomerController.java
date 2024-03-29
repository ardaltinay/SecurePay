package com.firisbe.SecurePay.controller;

import com.firisbe.SecurePay.dto.CustomerDto;
import com.firisbe.SecurePay.model.request.CreateCustomerRequest;
import com.firisbe.SecurePay.model.request.UpdateCreditCardInfoRequest;
import com.firisbe.SecurePay.model.response.GenericSuccessResponse;
import com.firisbe.SecurePay.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController implements IAbstractController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        List<CustomerDto> customers = customerService.getAllCustomers();
        GenericSuccessResponse successResponse = GenericSuccessResponse.builder()
                .message("All customers retrieving successfully!")
                .status(HttpStatus.OK)
                .data(customers)
                .timestamp(LocalDateTime.now())
                .build();
        return buildSuccessResponseEntity(successResponse);
    }

    @PostMapping
    public ResponseEntity<?> addCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        CustomerDto savedCustomer = customerService.addCustomer(request);
        GenericSuccessResponse successResponse = GenericSuccessResponse.builder()
                .message("Customer created successfully!")
                .status(HttpStatus.CREATED)
                .data(savedCustomer)
                .timestamp(LocalDateTime.now())
                .build();
        return buildSuccessResponseEntity(successResponse);
    }

    @PutMapping("/updateCreditCard")
    public ResponseEntity<?> updateCreditCardInfoByCustomerId(@Valid @RequestBody UpdateCreditCardInfoRequest request) {
        CustomerDto customerDto = customerService.updateCreditCardInfoByCustomerId(request);
        GenericSuccessResponse successResponse = GenericSuccessResponse.builder()
                .message("Customer credit card info updated successfully!")
                .status(HttpStatus.OK)
                .data(customerDto)
                .timestamp(LocalDateTime.now())
                .build();
        return buildSuccessResponseEntity(successResponse);
    }





}
