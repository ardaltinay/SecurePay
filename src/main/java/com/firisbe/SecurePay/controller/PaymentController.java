package com.firisbe.SecurePay.controller;

import com.firisbe.SecurePay.dto.PaymentDto;
import com.firisbe.SecurePay.model.request.CreatePaymentRequest;
import com.firisbe.SecurePay.model.response.GenericSuccessResponse;
import com.firisbe.SecurePay.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController implements IAbstractController{

    private final PaymentService paymentService;


    @PostMapping
    public ResponseEntity<?> addPayment(@Valid @RequestBody CreatePaymentRequest request) {
        PaymentDto paymentDto = paymentService.addPayment(request);
        GenericSuccessResponse successResponse = GenericSuccessResponse.builder()
                .message("Payment created successfully!")
                .status(HttpStatus.OK)
                .data(paymentDto)
                .timestamp(LocalDateTime.now())
                .build();
        return buildSuccessResponseEntity(successResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPayments(@RequestParam(required = false) String cardNumber,
                                            @RequestParam(required = false) Long customerNumber,
                                            @RequestParam(required = false) String customerName) {
        List<PaymentDto> paymentDtoList = paymentService.searchPaymentsByQueryParams(customerNumber, cardNumber, customerName);
        GenericSuccessResponse successResponse = GenericSuccessResponse.builder()
                .message("Payments retrieving by search criteria successfully!")
                .status(HttpStatus.OK)
                .data(paymentDtoList)
                .timestamp(LocalDateTime.now())
                .build();
        return buildSuccessResponseEntity(successResponse);
    }

    @GetMapping("/list")
    public ResponseEntity<?> listPaymentsByDateInterval(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                    @RequestParam(defaultValue = "0", required = false) int page,
                                                    @RequestParam(defaultValue = "5", required = false) int size) {
        List<PaymentDto> paymentDtoList = paymentService.listPaymentsByDateInterval(startDate, endDate, page, size);
        GenericSuccessResponse successResponse = GenericSuccessResponse.builder()
                .message("Payments retrieving by date interval successfully!")
                .status(HttpStatus.OK)
                .data(paymentDtoList)
                .timestamp(LocalDateTime.now())
                .build();
        return buildSuccessResponseEntity(successResponse);
    }


}
