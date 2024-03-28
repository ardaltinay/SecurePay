package com.firisbe.SecurePay.mapper;

import com.firisbe.SecurePay.dto.PaymentDto;
import com.firisbe.SecurePay.entity.CreditCard;
import com.firisbe.SecurePay.entity.Customer;
import com.firisbe.SecurePay.entity.Payment;
import com.firisbe.SecurePay.model.request.CreatePaymentRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentMapperTest {

    @InjectMocks
    private PaymentMapper paymentMapper;

    @Mock
    private CreatePaymentRequest createPaymentRequest;

    @Test
    void toEntity() {
        // given
        when(createPaymentRequest.getAmount()).thenReturn(new BigDecimal("100.00"));

        // when
        Payment payment = paymentMapper.toEntity(createPaymentRequest);

        // then
        assertEquals(new BigDecimal("100.00"), payment.getAmount());
    }

    @Test
    void entityToDto() {
        // Setup
        Payment payment = Payment.builder()
                .id(1L)
                .amount(new BigDecimal("100.00"))
                .createdAt(LocalDate.now())
                .customer(Customer.builder().name("Arda").email("arda@gmail.com").build())
                .creditCard(CreditCard.builder().encryptedCardNumber("1234567890123456")
                .cvvNumber(555L).expireDate(LocalDate.of(2024, 6, 1)).build())
                .build();

        // Test
        PaymentDto paymentDto = paymentMapper.entityToDto(payment);

        // Verify
        assertEquals(1L, paymentDto.getId());
        assertEquals(new BigDecimal("100.00"), paymentDto.getAmount());
        assertEquals("Arda", paymentDto.getCustomerName());
        assertEquals("arda@gmail.com", paymentDto.getCustomerEmail());
        assertEquals("1234567890123456", paymentDto.getCreditCardNumber());
        assertEquals(555L, paymentDto.getCreditCardCvvNumber());
        assertEquals(LocalDate.of(2024, 6, 1), paymentDto.getCreditCardExpireDate());
    }

    @Test
    void entityListToDtoList() {
        // given
        Payment payment1 = Payment.builder()
                .id(1L)
                .amount(new BigDecimal("100.00"))
                .createdAt(LocalDate.now())
                .customer(Customer.builder().name("Arda").email("arda@gmail.com").build())
                .creditCard(CreditCard.builder().encryptedCardNumber("1234567890123456")
                .cvvNumber(555L).expireDate(LocalDate.of(2024, 6, 1)).build())
                .build();
        Payment payment2 = Payment.builder()
                .id(2L)
                .amount(new BigDecimal("200.00"))
                .createdAt(LocalDate.now())
                .customer(Customer.builder().name("Berkay").email("berkay@gmail.com").build())
                .creditCard(CreditCard.builder().encryptedCardNumber("3453453645645")
                .cvvNumber(666L).expireDate(LocalDate.of(2024, 6, 1)).build())
                .build();
        List<Payment> payments = Arrays.asList(payment1, payment2);

        // when
        List<PaymentDto> paymentDtoList = paymentMapper.entityListToDtoList(payments);

        // then
        assertEquals(2, paymentDtoList.size());
        assertEquals(1L, paymentDtoList.get(0).getId());
        assertEquals(new BigDecimal("100.00"), paymentDtoList.get(0).getAmount());
        assertEquals("Arda", paymentDtoList.get(0).getCustomerName());
        assertEquals("arda@gmail.com", paymentDtoList.get(0).getCustomerEmail());
        assertEquals("1234567890123456", paymentDtoList.get(0).getCreditCardNumber());
        assertEquals(555L, paymentDtoList.get(0).getCreditCardCvvNumber());
        assertEquals(LocalDate.of(2024, 6, 1), paymentDtoList.get(0).getCreditCardExpireDate());
        assertEquals(2L, paymentDtoList.get(1).getId());
        assertEquals(new BigDecimal("200.00"), paymentDtoList.get(1).getAmount());
        assertEquals("Berkay", paymentDtoList.get(1).getCustomerName());
        assertEquals("berkay@gmail.com", paymentDtoList.get(1).getCustomerEmail());
        assertEquals("3453453645645", paymentDtoList.get(1).getCreditCardNumber());
        assertEquals(666L, paymentDtoList.get(1).getCreditCardCvvNumber());
        assertEquals(LocalDate.of(2024, 6, 1), paymentDtoList.get(1).getCreditCardExpireDate());
    }
}