package com.firisbe.SecurePay.mapper;

import com.firisbe.SecurePay.dto.CreditCardDto;
import com.firisbe.SecurePay.entity.CreditCard;
import com.firisbe.SecurePay.model.request.UpdateCreditCardInfoRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditCardMapperTest {

    @InjectMocks
    private CreditCardMapper creditCardMapper;

    @Mock
    private UpdateCreditCardInfoRequest updateCreditCardInfoRequest;

    @Test
    void toDto() {
        // given
        when(updateCreditCardInfoRequest.getCardNumber()).thenReturn("1234567890123456");
        when(updateCreditCardInfoRequest.getCvvNumber()).thenReturn(555L);
        when(updateCreditCardInfoRequest.getExpireDate()).thenReturn(LocalDate.of(2024, 6, 1));

        // when
        CreditCardDto creditCardDto = creditCardMapper.toDto(updateCreditCardInfoRequest);

        // then
        assertEquals("1234567890123456", creditCardDto.getCardNumber());
        assertEquals(555L, creditCardDto.getCvvNumber());
        assertEquals(LocalDate.of(2024, 6, 1), creditCardDto.getExpireDate());
    }

    @Test
    void toEntity() {
        // given
        when(updateCreditCardInfoRequest.getCardNumber()).thenReturn("1234567890123456");
        when(updateCreditCardInfoRequest.getCvvNumber()).thenReturn(555L);
        when(updateCreditCardInfoRequest.getExpireDate()).thenReturn(LocalDate.of(2024, 6, 1));

        // when
        CreditCard creditCard = creditCardMapper.toEntity(updateCreditCardInfoRequest);

        // then
        assertEquals("1234567890123456", creditCard.getEncryptedCardNumber());
        assertEquals(555L, creditCard.getCvvNumber());
        assertEquals(LocalDate.of(2024, 6, 1), creditCard.getExpireDate());
    }

    @Test
    void entityToDto() {
        // Setup
        CreditCard creditCard = CreditCard.builder()
                .id(1L)
                .encryptedCardNumber("1234567890123456")
                .cvvNumber(555L)
                .expireDate(LocalDate.of(2024, 6, 1))
                .build();

        // Test
        CreditCardDto creditCardDto = creditCardMapper.entityToDto(creditCard);

        // Verify
        assertEquals(1L, creditCardDto.getId());
        assertEquals("1234567890123456", creditCardDto.getCardNumber());
        assertEquals(555L, creditCardDto.getCvvNumber());
        assertEquals(LocalDate.of(2024, 6, 1), creditCardDto.getExpireDate());
    }
}