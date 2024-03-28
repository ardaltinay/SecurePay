package com.firisbe.SecurePay.mapper;

import com.firisbe.SecurePay.dto.PaymentDto;
import com.firisbe.SecurePay.entity.Payment;
import com.firisbe.SecurePay.model.request.CreatePaymentRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentMapper {

    public Payment toEntity(CreatePaymentRequest request) {
        return Payment.builder()
                .amount(request.getAmount())
                .build();
    }

    public PaymentDto entityToDto(Payment entity) {
        return PaymentDto.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .createdAt(entity.getCreatedAt())
                .customerName(entity.getCustomer().getName())
                .customerEmail(entity.getCustomer().getEmail())
                .creditCardNumber(entity.getCreditCard().getEncryptedCardNumber())
                .creditCardCvvNumber(entity.getCreditCard().getCvvNumber())
                .creditCardExpireDate(entity.getCreditCard().getExpireDate())
                .build();
    }

    public List<PaymentDto> entityListToDtoList(List<Payment> payments) {
        List<PaymentDto> paymentDtoList = new ArrayList<>();
        payments.forEach(payment -> {
            PaymentDto paymentDto = PaymentDto.builder()
                    .id(payment.getId())
                    .amount(payment.getAmount())
                    .createdAt(payment.getCreatedAt())
                    .customerName(payment.getCustomer().getName())
                    .customerEmail(payment.getCustomer().getEmail())
                    .creditCardNumber(payment.getCreditCard().getEncryptedCardNumber())
                    .creditCardCvvNumber(payment.getCreditCard().getCvvNumber())
                    .creditCardExpireDate(payment.getCreditCard().getExpireDate())
                    .build();

            paymentDtoList.add(paymentDto);
        });
        return paymentDtoList;
    }
}
