package com.firisbe.SecurePay.service;

import com.firisbe.SecurePay.entity.CreditCard;
import com.firisbe.SecurePay.exception.ResourceNotFoundException;
import com.firisbe.SecurePay.mapper.CreditCardMapper;
import com.firisbe.SecurePay.model.request.UpdateCreditCardInfoRequest;
import com.firisbe.SecurePay.repository.CreditCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditCardService {

    private final CreditCardRepository repository;

    protected CreditCard findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Credit Card not found with given id: " + id));
    }

    protected List<CreditCard> getCreditCardsByCardNumber(String cardNumber) {
        return repository.findByCardNumber(cardNumber);
    }
}
