package com.firisbe.SecurePay.service;

import com.firisbe.SecurePay.entity.CreditCard;
import com.firisbe.SecurePay.repository.CreditCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreditCardServiceTest {

    @Mock
    private CreditCardRepository cardRepository;

    @InjectMocks
    private CreditCardService cardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        // given
        CreditCard creditCard = new CreditCard(1L, "12341234", 555L,
                LocalDate.MAX, null, null);

        when(cardRepository.findById(creditCard.getId())).thenReturn(Optional.of(creditCard));

        // when
        CreditCard result = cardService.findById(1L);

        // then
        assertNotNull(result);
        assertEquals(creditCard.getId(), result.getId());

        verify(cardRepository).findById(creditCard.getId());
    }

    @Test
    void getCreditCardsByCardNumber() {
        // given
        CreditCard creditCard1 = new CreditCard(1L, "12341234", 555L,
                LocalDate.MAX, null, null);
        CreditCard creditCard2 = new CreditCard(2L, "12341234", 666L,
                LocalDate.MAX, null, null);
        List<CreditCard> creditCards = List.of(creditCard1, creditCard2);

        when(cardRepository.findByCardNumber("12341234")).thenReturn(creditCards);

        // when
        List<CreditCard> result = cardService.getCreditCardsByCardNumber("12341234");

        // then
        assertNotNull(result);
        assertEquals(creditCards.size(), result.size());

    }
}